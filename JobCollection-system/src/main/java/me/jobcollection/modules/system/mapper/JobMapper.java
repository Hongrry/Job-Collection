package me.jobcollection.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.jobcollection.modules.system.domain.Job;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-01 12:30
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {
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

    /**
     * 根据课程Id查询作业
     *
     * @param courseId
     * @return
     */
    List<Job> selectJobByCourseId(Long courseId);

    IPage<JobDto> selectJobDetail(Page<JobDto> page,
                                  @Param("keyword") String keyword,
                                  @Param("year") Integer year,
                                  @Param("month") Integer month,
                                  @Param("courseName") String courseName);

    /**
     * 分页条件查询用户的作业
     * @param page
     * @param userId
     * @param keyword
     * @param year
     * @param month
     * @param success
     * @param courseName
     * @return
     */
    IPage<JobDto> selectJobDetailById(Page<JobDto> page,
                                      @Param("userId") Long userId,
                                      @Param("keyword") String keyword,
                                      @Param("year") Integer year,
                                      @Param("month") Integer month,
                                      @Param("success") Boolean success,
                                      @Param("courseName") String courseName);

}
