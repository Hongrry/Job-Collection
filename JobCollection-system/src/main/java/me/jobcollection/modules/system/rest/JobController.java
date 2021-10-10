package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.utils.SpringSecurityUtils;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.exception.BadRequestException;
import me.jobcollection.modules.system.service.*;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobLogDto;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        // 当前用户
        JwtUserDto currentUser = SpringSecurityUtils.getCurrentUser();
        jobService.submitJob(jobLogDto, currentUser);
        return Result.success(null);
    }

    @PostMapping("publishJob")
    public Result publishJob(@RequestBody JobDto jobDto) {
        JwtUserDto currentUser = SpringSecurityUtils.getCurrentUser();

        if (currentUser == null || currentUser.getAuthorities().size() == 0) {
            throw new BadRequestException("非法访问");
        }
        // 更新
        jobService.publishJob(jobDto);
        // 关联
        return Result.success(null);
    }
}
