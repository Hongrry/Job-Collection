package me.jobcollection.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.jobcollection.modules.system.domain.Dept;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hongrry
 * @create 2021-10-08 14:25
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {
}
