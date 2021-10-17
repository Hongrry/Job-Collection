package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.common.rest.BaseController;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.domain.vo.UserVo;
import me.jobcollection.modules.system.service.UserService;
import me.jobcollection.modules.system.service.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.ref.ReferenceQueue;

/**
 * @author Hongrry
 * @create 2021-10-05 20:06
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController extends BaseController {
    private final UserService userService;

    @GetMapping("userInfo")
    public Result getUserInfo() {
        UserDto user = getCurrentUser();
        String avatar = "https://q1.qlogo.cn/g?b=qq&nk=" + StringUtils.substringBeforeLast(user.getEmail(), "@") + "&s=100";
        return Result.success(
                new UserVo(user.getUsername(),
                        user.getNickname(),
                        user.getEmail(),
                        avatar));
    }

    @PostMapping("update")
    public Result updateUserInfo(@RequestBody UserDto user, HttpServletRequest request) {
        UserDto userDto = getCurrentUser();
        String token = request.getHeader("Authorization");
        userService.updateUserInfo(user);
        userService.updateUserCache(userDto.getId(), token);
        return Result.success(null);
    }
}
