package me.jobcollection.modules.system.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import me.jobcollection.config.FileProperties;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.utils.SpringSecurityUtils;
import me.jobcollection.modules.system.domain.JobLog;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.domain.vo.JobLogVo;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.mapper.JobLogMapper;
import me.jobcollection.modules.system.mapper.JobMapper;
import me.jobcollection.modules.system.service.JobLogService;
import me.jobcollection.modules.system.service.dto.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-04 17:19
 */
@Service
@RequiredArgsConstructor
public class JobLogServiceImpl implements JobLogService {
    private final JobLogMapper jobLogMapper;
    private final JobMapper jobMapper;
    private final FileProperties properties;

    @Override
    public JobLog queryJobLog(Long userId, Long jobId, boolean isSuccess) {
        LambdaQueryWrapper<JobLog> queryWrapper =
                new LambdaQueryWrapper<>();
        queryWrapper.eq(JobLog::getSuccess, isSuccess);
        queryWrapper
                .eq(JobLog::getJobId, jobId)
                .eq(JobLog::getUserId, userId)
                .orderByDesc(JobLog::getLogId)
                .last("limit 1");
        return jobLogMapper.selectOne(queryWrapper);
    }

    @Override
    public void addSuccessLog(Long jobId, String newPath, JwtUserDto currentUser) {
        JobLog jobLog = new JobLog();
        jobLog.setJobId(jobId);
        jobLog.setDate(System.currentTimeMillis());
        jobLog.setAddress(newPath);
        jobLog.setSuccess(true);
        jobLog.setUserId(currentUser.getUser().getId());
        jobLogMapper.insert(jobLog);
    }

    @Override
    public Result listJobLogDetail(JobLogQueryCriteria jobLogQueryCriteria) {
        // 分页
        Page<JobLogDetail> page = new Page<>(jobLogQueryCriteria.getPage(), jobLogQueryCriteria.getPageSize());

        JwtUserDto currentUser = SpringSecurityUtils.getCurrentUser();
        IPage<JobLogDetail> iPage = jobLogMapper.listJobLogDetail(page,
                currentUser.getUser().getId(),
                jobLogQueryCriteria.getYear(),
                jobLogQueryCriteria.getMonth(),
                jobLogQueryCriteria.getDay(),
                jobLogQueryCriteria.getSuccess(),
                jobLogQueryCriteria.getKeyword()
        );

        List<JobLogVo> list = convertList(iPage.getRecords());

        HashMap<String, Object> map = new HashMap<String, Object>(2) {
            {
                put("total", iPage.getTotal());
                put("list", list);
            }
        };

        return Result.success(map);
    }

    @Override
    public EmailVo sendEmail(Long jobId, String path, JwtUserDto currentUser) {
        JobDto jobDto = jobMapper.queryJobDetailById(jobId);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email/taskAlarm.ftl");
        String content = template.render(Dict.create()
                .set("CourseName", jobDto.getCourseName())
                .set("JobName", jobDto.getJobName()));
        UserDto user = currentUser.getUser();
        EmailVo emailVo = new EmailVo(Collections.singletonList(user.getEmail()), "提交作业", content);
        return emailVo;
    }

    @Override
    public void addErrorLog(Long jobId, String description) {
        JobLog jobLog = new JobLog();
        jobLog.setJobId(jobId);
        jobLog.setDate(System.currentTimeMillis());
        jobLog.setSuccess(false);
        jobLog.setDescription(description);
        jobLog.setUserId(SpringSecurityUtils.getCurrentUser().getUser().getId());
        jobLogMapper.insert(jobLog);
    }

    @Override
    public void deleteLogByJobId(Long jobId) {
        jobLogMapper.deleteLogByJobId(jobId);
    }

    private List<JobLogVo> convertList(List<JobLogDetail> list) {
        ArrayList<JobLogVo> jobLogVos = new ArrayList<>(list.size());
        for (JobLogDetail detail : list) {
            JobLogVo convert = convert(detail);
            jobLogVos.add(convert);
        }
        return jobLogVos;
    }

    private JobLogVo convert(JobLogDetail jobLogDetail) {
        JobLogVo jobLogVo = new JobLogVo();
        BeanUtils.copyProperties(jobLogDetail, jobLogVo);

        JobDto jobDto = jobMapper.queryJobDetailById(jobLogDetail.getJobId());
        jobLogVo.setJobName(jobDto.getJobName());
        jobLogVo.setCourseName(jobDto.getCourseName());
        return jobLogVo;
    }

}
