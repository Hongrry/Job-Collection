package me.jobcollection.modules.system.service;

import me.jobcollection.modules.system.domain.Course;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-07 20:18
 */
public interface CourseService {
    /**
     * 查询所有的课程
     *
     * @return
     */
    List<Course> listCourse();
}
