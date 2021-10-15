package me.jobcollection.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.jobcollection.modules.system.domain.Course;
import me.jobcollection.modules.system.service.dto.CourseQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Hongrry
 * @create 2021-10-04 16:40
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 分页查询当前用户的所有课程
     *
     * @param page
     * @param UserId
     * @return
     */
    IPage<Course> listCourseByUserId(Page<Course> page, Long UserId);

    /**
     * 分页条件查询
     *
     * @param page
     * @param keyword
     * @param teacher
     * @return
     */
    IPage<Course> listCourse(Page<Course> page,
                             @Param("keyword") String keyword,
                             @Param("teacher") String teacher);
}
