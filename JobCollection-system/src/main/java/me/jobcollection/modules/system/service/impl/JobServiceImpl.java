package me.jobcollection.modules.system.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.utils.SpringSecurityUtils;
import me.jobcollection.modules.system.domain.Job;
import me.jobcollection.modules.system.domain.JobLog;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.domain.vo.JobVo;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.mapper.JobMapper;
import me.jobcollection.modules.system.service.EmailService;
import me.jobcollection.modules.system.service.FileService;
import me.jobcollection.modules.system.service.JobLogService;
import me.jobcollection.modules.system.service.JobService;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobLogDto;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import me.jobcollection.modules.system.service.dto.UserDto;
import me.jobcollection.utils.enums.JobStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final JobLogService jobLogService;
    private final FileService fileService;
    private final EmailService emailService;

    @Override
    public Result listJobDetail(JobQueryCriteria jobQueryCriteria) {
        Page<JobDto> page = new Page<>(jobQueryCriteria.getPage(), jobQueryCriteria.getPageSize());

        IPage<JobDto> detail = jobMapper.listJob(page,
                SpringSecurityUtils.getCurrentUser().getUser().getId(),
                jobQueryCriteria.getYear(),
                jobQueryCriteria.getMonth(),
                jobQueryCriteria.getKeyword(),
                null,
                jobQueryCriteria.getCourseName()
        );

        List<JobVo> jobVos = convertList(detail.getRecords(), true);

        HashMap<String, Object> map = new HashMap<String, Object>(2) {
            {
                put("total", page.getTotal());
                put("list", jobVos);
            }
        };

        return Result.success(map);

    }

    @Override
    public IPage<JobDto> listJob(JobQueryCriteria jobQueryCriteria) {
        UserDto user = SpringSecurityUtils.getCurrentUser().getUser();
        Page<JobDto> page = new Page<>(jobQueryCriteria.getPage(), jobQueryCriteria.getPageSize());

        return jobMapper.listJob(page, user.getId(),
                jobQueryCriteria.getYear(),
                jobQueryCriteria.getMonth(),
                jobQueryCriteria.getKeyword(),
                jobQueryCriteria.getSuccess(),
                null);
    }

    @Override
    public Result listUserJobByMonth(JobQueryCriteria jobQueryCriteria) {

        // 查询用户本月的所有 Job
        IPage<JobDto> page = listJob(jobQueryCriteria);
        List<JobVo> finalJobVos = convertList(page.getRecords(), true);

        HashMap<String, Object> map = new HashMap<String, Object>(2) {
            {
                put("total", page.getTotal());
                put("list", finalJobVos);
            }
        };

        return Result.success(map);
    }

    @Override
    public Job queryJobById(Long jobId) {
        return jobMapper.selectById(jobId);
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
        job.setTemplateId(jobDto.getTemplateId());
        job.setCourseId(jobDto.getCourseId());
        jobMapper.insert(job);

        jobMapper.addJobToDept(jobDto.getDeptId(), job.getJobId());
    }

    @Override
    public void submitJob(JobLogDto jobLogDto, JwtUserDto currentUser) {
        // 查询作业的详细
        JobDto jobDto = queryJobDetailById(jobLogDto.getJobId());
        // 作业是否截止
        if (jobDto.getDeadline() > System.currentTimeMillis()) {

            // 处理文件
            String newPath = fileService.handleFile(jobDto, jobLogDto.getFileUrl(), currentUser);
            // 更新数据库
            jobLogService.addSuccessLog(jobDto.getJobId(), newPath, currentUser);
            // 邮件通知
            EmailVo emailVo = jobLogService.sendEmail(jobLogDto.getJobId(), newPath, currentUser);
            emailService.send(emailVo);
        }
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

        // 转换时间格式
        String s = new DateTime(job.getDeadline()).toString("yyyy-MM-dd HH:mm");
        jobVo.setDeadline(s);

        s = new DateTime(job.getBeginTime()).toString("yyyy-MM-dd HH:mm");
        jobVo.setBeginTime(s);
        jobVo.setJobName(job.getJobName());
        // 获取当前学号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserDto principal = (JwtUserDto) authentication.getPrincipal();

        if (status) {
            // 设置作业状态
            JobLog jobLog = jobLogService.queryJobLog(principal.getUser().getId(), job.getJobId(), true);
            if (jobLog != null && jobLog.getSuccess()) {
                // 成功提交
                jobVo.setStatus(JobStatus.SUBMITTED.getCode());
            } else {
                // 没有成功的提交
                jobVo.setStatus(JobStatus.NONE.getCode());
            }
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > job.getDeadline()) {
            // 已过截止日期
            jobVo.setIsExpire(true);
        }
        return jobVo;
    }
}
