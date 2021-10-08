package me.jobcollection.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.jobcollection.modules.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Hongrry
 * @create 2021-10-01 12:30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
