package me.jobcollection.modules.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
public class JobController extends BaseController {
    private final JobService jobService;

    @GetMapping
    public Result listJobDetail(JobQueryCriteria criteria) {
        IPage<JobDto> page = jobService.listJobDetails(criteria);
        return handlerPageData(page);
    }

    @GetMapping("listJobByUserId")
    public Result listJobDetailByUserId(JobQueryCriteria criteria) {
        return jobService.listJobDetailByUserId(criteria, getCurrentUserId());
    }


    @GetMapping("/listUserJobByMonth")
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
        jobService.submitJob(jobLogDto, getCurrentLoginUser());
        return Result.success(null);
    }

    /**
     * deptID 要不要剥离
     *
     * @param jobDto
     * @return
     */
    @PostMapping
    public Result addJob(@RequestBody JobDto jobDto) {

        // 更新
        jobService.publishJob(jobDto);
        // 关联
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
}
