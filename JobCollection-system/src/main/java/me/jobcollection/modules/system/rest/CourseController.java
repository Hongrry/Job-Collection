package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.Course;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.CourseService;
import me.jobcollection.modules.system.service.dto.CourseQueryCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-07 20:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("course")
public class CourseController extends BaseController {
    private final CourseService courseService;

    @GetMapping
    public Result listCourse(CourseQueryCriteria criteria) {
        return handlerPageData(courseService.listCourse(criteria));
    }

    @PostMapping
    public Result addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return Result.success(null);
    }

    @DeleteMapping
    public Result deleteCourse(@RequestBody Long courseId) {
        courseService.deleteCourse(courseId);
        return Result.success(null);
    }

    @PutMapping
    public Result updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
        return Result.success(null);
    }

    /**
     * 只实现了分页查询,条件查询暂不需要
     * 2021年10月14日19:52:12
     *
     * @param criteria
     * @return
     */
    @GetMapping("listCourse")
    public Result listCourseByUserId(CourseQueryCriteria criteria) {
        return handlerPageData(courseService.listCourseByUserId(criteria, getCurrentUserId()));
    }
}
