package me.jobcollection.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.system.domain.Job;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobLogDto;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import org.springframework.scheduling.annotation.Async;

/**
 * @author Hongrry
 * @create 2021-10-04 16:01
 */
public interface JobService {

    /**
     * @param jobQueryCriteria
     * @return
     */
    Result listJobDetail(JobQueryCriteria jobQueryCriteria);

    /**
     * 根据 条件 查询作业
     *
     * @param jobQueryCriteria 条件
     * @return
     */
    IPage<JobDto> listJob(JobQueryCriteria jobQueryCriteria);

    /**
     * 根据 用户 id 和日期 查询作业
     *
     * @param jobQueryCriteria
     * @return
     */
    Result listUserJobByMonth(JobQueryCriteria jobQueryCriteria);

    /**
     * 根据 id 查询 job
     *
     * @param jobId
     * @return
     */
    Job queryJobById(Long jobId);

    /**
     * 查询 作业详细
     *
     * @param jobId
     * @return
     */
    JobDto queryJobDetailById(Long jobId);

    /**
     * 发布新的作业
     *
     * @param jobDto
     */
    void publishJob(JobDto jobDto);

    /**
     * 提交作业
     *
     * @param jobLogDto
     */
    @Async("taskExecutor")
    void submitJob(JobLogDto jobLogDto, JwtUserDto currentUser);
}
