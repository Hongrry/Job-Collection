package me.jobcollection.modules.security.utils;

import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.system.exception.BadRequestException;
import me.jobcollection.modules.system.service.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Hongrry
 * @create 2021-10-05 13:51
 */
public class SpringSecurityUtils {
    /**
     * 获取当前登录的用户
     *
     * @return
     */
    public static JwtUserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException("未登录");
        }
        return (JwtUserDto) authentication.getPrincipal();
    }

}
