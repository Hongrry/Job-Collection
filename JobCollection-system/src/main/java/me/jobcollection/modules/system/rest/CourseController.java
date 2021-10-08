package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.Course;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-07 20:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public Result listCourse() {
        return Result.success(courseService.listCourse());
    }
}
