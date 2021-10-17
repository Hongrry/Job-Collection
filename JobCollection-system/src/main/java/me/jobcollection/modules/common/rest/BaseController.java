package me.jobcollection.modules.common.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.utils.SecurityUtils;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.dto.UserDto;

import java.util.HashMap;

/**
 * @author Hongrry
 * @create 2021-10-14 19:04
 */
public class BaseController {
    public UserDto getCurrentUser() {
        return SecurityUtils.getUser();
    }

    public JwtUserDto getCurrentLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    public Long getCurrentUserId() {
        return SecurityUtils.getUserId();
    }

    public Result handlerPageData(IPage<?> pageData) {
        HashMap<String, Object> map = new HashMap<String, Object>(2) {
            {
                put("total", pageData.getTotal());
                put("list", pageData.getRecords());
            }
        };
        return Result.success(map);
    }
}
