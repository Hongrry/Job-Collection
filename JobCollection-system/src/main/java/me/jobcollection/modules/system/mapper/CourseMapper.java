package me.jobcollection.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.jobcollection.modules.system.domain.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hongrry
 * @create 2021-10-04 16:40
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
