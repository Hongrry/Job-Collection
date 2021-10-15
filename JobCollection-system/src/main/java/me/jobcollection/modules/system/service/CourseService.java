package me.jobcollection.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.jobcollection.modules.system.domain.Course;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.dto.CourseQueryCriteria;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-07 20:18
 */
public interface CourseService {
    /**
     * 通过 UserId 获取课程
     *
     * @param criteria
     * @param currentUserId
     * @return
     */
    IPage<Course> listCourseByUserId(CourseQueryCriteria criteria, Long currentUserId);

    /**
     * 条件查询所有的课程
     *
     * @param criteria
     * @return
     */
    IPage<Course> listCourse(CourseQueryCriteria criteria);

    /**
     * 新增课程
     *
     * @param course
     */
    void addCourse(Course course);

    /**
     * 删除课程
     *
     * @param course
     */
    void deleteCourse(Long course);

    /**
     * 更新课程
     *
     * @param course
     */
    void updateCourse(Course course);
}
