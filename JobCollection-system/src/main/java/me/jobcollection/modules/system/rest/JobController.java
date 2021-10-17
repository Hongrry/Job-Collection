package me.jobcollection.modules.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.common.rest.BaseController;
import me.jobcollection.modules.system.domain.vo.Result;
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
public class JobController extends BaseController {
    private final JobService jobService;


    @GetMapping
    public Result listJobDetail(JobQueryCriteria criteria) {
        IPage<JobDto> page = jobService.listJobDetail(criteria);
        return handlerPageData(page);
    }

    @PostMapping
    public Result addJob(@RequestBody JobDto jobDto) {
        // 更新
        jobService.publishJob(jobDto);
        return Result.success(null);
    }

    @PutMapping
    public Result updateJob(@RequestBody JobDto jobDto) {
        return jobService.updateJob(jobDto);
    }

    @DeleteMapping
    public Result deleteJob(@RequestBody JobDto jobDto) {
        jobService.deleteJob(jobDto.getJobId());
        return Result.success(null);
    }

    @GetMapping("listJobByUserId")
    public Result listJobDetailByUserId(JobQueryCriteria criteria) {
        return jobService.listJobDetailByUserId(criteria, getCurrentUserId());
    }

    /**
     * 1. 验证日期
     * 2. 移动文件
     * 3. 写入数据库
     */
    @PostMapping("submitJob")
    public Result submitJob(@Validated @RequestBody JobLogDto jobLogDto) {
        jobService.submitJob(jobLogDto, getCurrentLoginUser());
        return Result.success(null);
    }
}
