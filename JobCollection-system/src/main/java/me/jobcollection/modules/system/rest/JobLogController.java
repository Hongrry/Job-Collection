package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.vo.JobLogVo;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.JobLogService;
import me.jobcollection.modules.system.service.dto.JobLogQueryCriteria;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hongrry
 * @create 2021-10-07 18:29
 */
@RestController
@RequestMapping("logs")
@RequiredArgsConstructor
public class JobLogController {
    private final JobLogService jobLogService;

    @GetMapping("listJobHistory")
    public Result listJobHistory(JobLogQueryCriteria criteria) {

        return jobLogService.listJobLogDetail(criteria);
    }
}
