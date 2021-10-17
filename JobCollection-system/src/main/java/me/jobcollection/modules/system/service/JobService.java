package me.jobcollection.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.system.domain.Job;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobLogDto;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-04 16:01
 */
public interface JobService {

    /**
     * 分页条件查询所有的作业
     *
     * @param criteria
     * @return
     */
    IPage<JobDto> listJobDetail(JobQueryCriteria criteria);

    /**
     * 根据 id 查询用户的作业
     *
     * @param jobQueryCriteria
     * @param userId
     * @return
     */
    Result listJobDetailByUserId(JobQueryCriteria jobQueryCriteria, Long userId);

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
     * @param currentUser
     */
    void submitJob(JobLogDto jobLogDto, JwtUserDto currentUser);

    /**
     * 更新作业
     *
     * @param jobDto
     * @return
     */
    Result updateJob(JobDto jobDto);

    /**
     * 删除作业
     *
     * @param id
     * @return
     */
    void deleteJob(Long id);

    /**
     * 批量删除作业
     *
     * @param ids
     * @return
     */
    void deleteBatchJob(List<Long> ids);

    /**
     * 通过课程ID查询作业
     *
     * @param courseId
     * @return
     */
    List<Job> selectJobByCourseId(Long courseId);

}
