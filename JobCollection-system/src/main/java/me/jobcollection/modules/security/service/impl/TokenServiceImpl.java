package me.jobcollection.modules.security.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.utils.JwtUtils;
import me.jobcollection.modules.security.service.TokenService;
import me.jobcollection.modules.system.service.UserService;
import me.jobcollection.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Hongrry
 * @create 2021-10-02 11:23
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserService userService;
    private final RedisUtils redisUtils;

    @Override
    public JwtUserDto checkToken(String token) throws JsonProcessingException {
        Map<String, Object> map = JwtUtils.checkToken(token);
        if (map != null) {
            String s = "TOKEN_" + token;
            return (JwtUserDto) redisUtils.get(s);
        }
        return null;
    }

    @Override
    public String createToken(JwtUserDto jwtUserDto) {
        String token = JwtUtils.createToken(jwtUserDto.getUser().getId());
        redisUtils.set("TOKEN_" + token, jwtUserDto, 7, TimeUnit.DAYS);
        return token;
    }

    @Override
    public void deleteToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            redisUtils.del("TOKEN_" + token);
        }
    }
}
