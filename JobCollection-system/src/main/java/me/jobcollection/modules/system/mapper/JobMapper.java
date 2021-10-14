package me.jobcollection.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.jobcollection.modules.system.domain.Job;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-01 12:30
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {
    /**
     * 分页条件查询
     *
     * @param page
     * @param userId
     * @param year
     * @param month
     * @param keyword
     * @param success
     * @return
     */
    IPage<JobDto> listJob(Page<JobDto> page,
                          Long userId,
                          Integer year,
                          Integer month,
                          String keyword,
                          Boolean success,
                          String courseName
    );

    /**
     * 查询作业 详细
     *
     * @param jobId
     * @return
     */
    JobDto queryJobDetailById(Long jobId);

    /**
     * 更新关联
     *
     * @param deptId
     * @param jobId
     */
    void addJobToDept(Long deptId, Long jobId);

    /**
     * 删除关联
     *
     * @param jobId
     */
    void deleteJobFromDept(Long jobId);

    /**
     * 更新关联
     *
     * @param jobId
     * @param deptId
     */
    void updateJobFromDept(Long jobId, Long deptId);
}
