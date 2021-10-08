package me.jobcollection.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.Course;
import me.jobcollection.modules.system.mapper.CourseMapper;
import me.jobcollection.modules.system.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-07 20:19
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;

    @Override
    public List<Course> listCourse() {
        return courseMapper.selectList(null);
    }
}
