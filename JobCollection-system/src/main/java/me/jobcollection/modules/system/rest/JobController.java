package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.domain.vo.JobLogVo;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.EmailService;
import me.jobcollection.modules.system.service.FileService;
import me.jobcollection.modules.system.service.JobLogService;
import me.jobcollection.modules.system.service.JobService;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobLogDto;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Hongrry
 * @create 2021-10-04 15:38
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("job")
public class JobController {
    private final JobService jobService;
    private final FileService fileService;
    private final JobLogService jobLogService;
    private final EmailService emailService;

    @GetMapping
    public Result listJob(JobQueryCriteria criteria) {
        return jobService.listJobDetail(criteria);
    }


    @GetMapping("/listJob")
    public Result listUserJobByMonth(JobQueryCriteria criteria) {
        return jobService.listUserJobByMonth(criteria);
    }

    /**
     * 1. 验证日期
     * 2. 移动文件
     * 3. 写入数据库
     */
    @PostMapping("submitJob")
    public Result submitJob(@Validated @RequestBody JobLogDto jobLogDto) {
        // 查询作业的详细
        JobDto jobDto = jobService.queryJobDetailById(jobLogDto.getJobId());

        // 作业是否截止
        if (jobDto.getDeadline() > System.currentTimeMillis()) {
            // 处理文件
            String newPath = fileService.handleFile(jobDto, jobLogDto.getFileUrl());
            // 更新数据库
            jobLogService.addSuccessLog(jobDto.getJobId(), newPath);
            // 邮件通知
            EmailVo emailVo = jobLogService.sendEmail(jobLogDto.getJobId(), newPath);
            emailService.send(emailVo);

        }
        return Result.success(null);
    }


}
