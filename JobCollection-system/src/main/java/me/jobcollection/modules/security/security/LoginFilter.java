package me.jobcollection.modules.security.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.service.TokenService;
import me.jobcollection.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HONGRRY
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 从请求头中获取token字符串并解析（JwtManager之前文章有详解，这里不多说了）
        String token = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(token)) {

            JwtUserDto jwtUserDto = tokenService.checkToken(token);
            // 手动组装一个认证对象
            if (jwtUserDto != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUserDto, jwtUserDto.getPassword(), jwtUserDto.getAuthorities());
                // 将认证对象放到上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        chain.doFilter(request, response);
    }
}