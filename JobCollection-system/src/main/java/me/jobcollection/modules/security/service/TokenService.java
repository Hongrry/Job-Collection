package me.jobcollection.modules.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.jobcollection.modules.security.service.dto.JwtUserDto;

/**
 * @author Hongrry
 * @create 2021-10-02 11:24
 */
public interface TokenService {
    /**
     * 验证 token 并且返回 验证的数据
     *
     * @param token
     * @return
     * @throws JsonProcessingException
     */

    JwtUserDto checkToken(String token) throws JsonProcessingException;

    /**
     * 创建 token 和缓存
     *
     * @param jwtUserDto
     * @return
     * @throws JsonProcessingException
     */
    String createToken(JwtUserDto jwtUserDto, Boolean rememberMe) throws JsonProcessingException;

    /**
     * 删除token
     *
     * @param token
     */
    void deleteToken(String token);
}
