package me.jobcollection.modules.security.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.config.RsaProperties;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.security.service.TokenService;
import me.jobcollection.modules.security.service.dto.AuthUserDto;
import me.jobcollection.utils.RsaUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * @author Hongrry
 * @create 2021-10-01 12:47
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("login")
    public Result login(@Validated @RequestBody AuthUserDto authUser,
                        HttpServletRequest request) throws Exception {

        // 密码私钥解密
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        // 认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 放到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /* 创建token */
        JwtUserDto principal = (JwtUserDto) authentication.getPrincipal();
        String token = tokenService.createToken(principal, authUser.getRememberMe());

        HashMap<String, String> res = new HashMap<String, String>(3) {
            {
                put("username", principal.getUsername());
                put("token", token);
            }
        };
        return Result.success(res);
    }

    @PostMapping("logout")
    public Result logout(HttpServletRequest request) {
        tokenService.deleteToken(request.getHeader("Authorization"));
        SecurityContextHolder.clearContext();
        return Result.success(null);
    }
}
