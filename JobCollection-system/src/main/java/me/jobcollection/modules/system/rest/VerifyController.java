package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.security.utils.SpringSecurityUtils;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.exception.BadRequestException;
import me.jobcollection.modules.system.service.EmailService;
import me.jobcollection.modules.system.service.VerifyService;
import me.jobcollection.modules.system.service.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author HONGRRY
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
public class VerifyController {

    private final VerifyService verificationCodeService;
    private final EmailService emailService;

    @GetMapping
    public Result resetEmail() {
        UserDto user = SpringSecurityUtils.getCurrentUser().getUser();
        EmailVo emailVo = verificationCodeService.sendEmail(user.getEmail(), "CODE_");
        emailService.send(emailVo);
        return Result.success(null);
    }

    @PostMapping(value = "/validated")
    public Result validated(@RequestBody String code) {
        if (StringUtils.isNotEmpty(code)) {
            UserDto user = SpringSecurityUtils.getCurrentUser().getUser();
            verificationCodeService.validated("CODE_" + user.getEmail(), code.replaceAll("\"",""));
        } else {
            throw new BadRequestException("验证码不能为空");
        }

        return Result.success(null);
    }
}