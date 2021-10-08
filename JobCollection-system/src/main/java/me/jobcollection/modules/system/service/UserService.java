package me.jobcollection.modules.system.service;

import me.jobcollection.modules.system.domain.User;
import me.jobcollection.modules.system.service.dto.UserDto;

/**
 * @author Hongrry
 * @create 2021-10-01 13:00
 */
public interface UserService {
    /**
     * 根据账号查询用户
     *
     * @param account  账号
     * @param password 密码
     * @return SysUser
     */
    UserDto findUserByAccount(String account, String password);

    /**
     * 根据账号查询用户
     *
     * @param account 账号
     * @return SysUser
     */
    UserDto findUserByAccount(String account);

    /**
     * 根据账号查询用户
     *
     * @param userId 用户Id
     * @return SysUser
     */
    UserDto findUserByUserId(Long userId);

    /**
     * 更新用户信息
     *
     * @param user
     */
    void updateUserInfo(UserDto user);

    /**
     * 更新用户缓存
     *
     * @param userId
     * @param token
     *
     */
    void updateUserCache(Long userId,String token);
}
