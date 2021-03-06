package me.jobcollection.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.utils.SecurityUtils;
import me.jobcollection.modules.system.domain.Course;
import me.jobcollection.modules.system.domain.Dept;
import me.jobcollection.modules.system.domain.Job;
import me.jobcollection.modules.system.domain.JobLog;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.domain.vo.JobVo;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.common.exception.BadRequestException;
import me.jobcollection.modules.system.mapper.CourseMapper;
import me.jobcollection.modules.system.mapper.DeptMapper;
import me.jobcollection.modules.system.mapper.JobMapper;
import me.jobcollection.modules.system.service.EmailService;
import me.jobcollection.modules.system.service.FileService;
import me.jobcollection.modules.system.service.JobLogService;
import me.jobcollection.modules.system.service.JobService;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobLogDto;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import me.jobcollection.modules.common.utils.enums.JobStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-04 16:08
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobMapper jobMapper;
    private final DeptMapper deptMapper;
    private final CourseMapper courseMapper;
    private final JobLogService jobLogService;
    private final FileService fileService;
    private final EmailService emailService;


    @Override
    public Result listJobDetailByUserId(JobQueryCriteria criteria, Long userId) {
        Page<JobDto> page = new Page<>(criteria.getPage(), criteria.getPageSize());
        IPage<JobDto> iPage = jobMapper.selectJobDetailById(page,
                userId,
                criteria.getKeyword(),
                criteria.getYear(),
                criteria.getMonth(),
                criteria.getSuccess(),
                criteria.getCourseName()
        );

        List<JobVo> jobVos = convertList(iPage.getRecords(), true);

        HashMap<String, Object> map = new HashMap<String, Object>(2) {
            {
                put("total", iPage.getTotal());
                put("list", jobVos);
            }
        };
        return Result.success(map);
    }

    @Override
    public JobDto queryJobDetailById(Long jobId) {
        return jobMapper.queryJobDetailById(jobId);
    }

    @Override
    public void publishJob(JobDto jobDto) {
        Job job = new Job();
        job.setJobName(jobDto.getJobName());
        job.setBeginTime(System.currentTimeMillis());
        job.setDeadline(jobDto.getDeadline());
        // ????????????????????????
        Course course = courseMapper.selectById(jobDto.getCourseId());
        if (course == null) {
            throw new BadRequestException("???????????????");
        }
        job.setCourseId(jobDto.getCourseId());
        jobMapper.insert(job);

        jobMapper.addJobToDept(jobDto.getDeptId(), job.getJobId());
    }

    @Override
    public void submitJob(JobLogDto jobLogDto, JwtUserDto currentUser) {
        // ?????????????????????
        JobDto jobDto = queryJobDetailById(jobLogDto.getJobId());
        // ?????????????????? (????????????????????? ??????????????????????????? ???????????????????????????????????????????????????????????????)
        if (jobDto.getDeadline() > System.currentTimeMillis()) {

            // ????????????
            String newPath = fileService.handleFile(jobDto, jobLogDto.getFileUrl(), currentUser);
            // ???????????????
            jobLogService.addSuccessLog(jobDto.getJobId(), newPath, currentUser);
            // ????????????
            EmailVo emailVo = jobLogService.sendEmail(jobDto, newPath, currentUser);
            emailService.send(emailVo);
        } else {
            throw new BadRequestException("????????????????????????");
        }
    }

    @Override
    public Result updateJob(JobDto jobDto) {
        checkJob(jobDto.getJobId());
        Job job = new Job();
        job.setJobId(jobDto.getJobId());
        job.setJobName(jobDto.getJobName());
        job.setDeadline(jobDto.getDeadline());
        job.setCourseId(jobDto.getCourseId());
        jobMapper.updateById(job);
        // ????????????????????? ????????????
        Dept dept = deptMapper.queryJobDeptByJobId(jobDto.getJobId());
        if (!dept.getId().equals(jobDto.getDeptId())) {
            jobMapper.updateJobFromDept(jobDto.getJobId(), jobDto.getDeptId());
        }
        return Result.success(null);
    }

    @Override
    public void deleteJob(Long id) {
        checkJob(id);

        jobMapper.deleteById(id);
        jobMapper.deleteJobFromDept(id);

        /*??????????????????*/
        jobLogService.deleteLogByJobId(id);
    }

    @Override
    public void deleteBatchJob(List<Long> ids) {
        for (Long id : ids) {
            deleteJob(id);
        }
    }

    @Override
    public List<Job> selectJobByCourseId(Long courseId) {
        return jobMapper.selectJobByCourseId(courseId);
    }

    @Override
    public IPage<JobDto> listJobDetail(JobQueryCriteria criteria) {
        Page<JobDto> page = new Page<>(criteria.getPage(), criteria.getPageSize());
        return jobMapper.selectJobDetail(page,
                criteria.getKeyword(),
                criteria.getYear(),
                criteria.getMonth(),
                criteria.getCourseName());
    }

    private List<JobVo> convertList(List<JobDto> jobList, boolean status) {
        ArrayList<JobVo> jobVos = new ArrayList<>(jobList.size());
        for (JobDto job : jobList) {
            JobVo jobVo = convert(job, status);
            jobVos.add(jobVo);
        }
        return jobVos;
    }


    private JobVo convert(JobDto job, boolean status) {
        JobVo jobVo = new JobVo();
        BeanUtils.copyProperties(job, jobVo);

        // ??????????????????
        Long userId = SecurityUtils.getUserId();
        if (status) {
            // ??????????????????
            JobLog jobLog = jobLogService.queryJobLog(userId, job.getJobId(), true);
            if (jobLog != null && jobLog.getSuccess()) {
                // ????????????
                jobVo.setStatus(JobStatus.SUBMITTED.getCode());
            } else {
                // ?????????????????????
                jobVo.setStatus(JobStatus.NONE.getCode());
            }
        }
        return jobVo;
    }

    private void checkJob(Long jobId) {
        Job job = jobMapper.selectById(jobId);
        if (job == null) {
            throw new BadRequestException("???????????????");
        }
    }
}
