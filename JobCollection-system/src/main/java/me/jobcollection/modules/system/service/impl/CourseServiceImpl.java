package me.jobcollection.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.Course;
import me.jobcollection.modules.system.domain.Job;
import me.jobcollection.modules.common.exception.BadRequestException;
import me.jobcollection.modules.system.mapper.CourseMapper;
import me.jobcollection.modules.system.service.CourseService;
import me.jobcollection.modules.system.service.JobService;
import me.jobcollection.modules.system.service.dto.CourseQueryCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hongrry
 * @create 2021-10-07 20:19
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;
    private final JobService jobService;

    @Override
    public IPage<Course> listCourseByUserId(CourseQueryCriteria criteria, Long currentUserId) {
        Page<Course> page = new Page<>(criteria.getPage(), criteria.getPageSize());
        return courseMapper.listCourseByUserId(page, currentUserId);
    }

    @Override
    public IPage<Course> listCourse(CourseQueryCriteria criteria) {
        Page<Course> page = new Page<>(criteria.getPage(), criteria.getPageSize());
        return courseMapper.listCourse(page, criteria.getKeyword(), criteria.getTeacher());
    }

    @Override
    public void addCourse(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        checkCourse(courseId);
        // 删除课程
        courseMapper.deleteById(courseId);
        // 删除对应的作业
        List<Job> jobList = jobService.selectJobByCourseId(courseId);
        jobService.deleteBatchJob(jobList.stream().map(Job::getJobId).collect(Collectors.toList()));
    }

    @Override
    public void updateCourse(Course course) {
        checkCourse(course.getId());
        courseMapper.updateById(course);
    }

    private void checkCourse(Long courseId) {
        // 检查课程是否存在
        Course c = courseMapper.selectById(courseId);
        if (c.getId() == null) {
            throw new BadRequestException("课程不存在");
        }
    }
}
