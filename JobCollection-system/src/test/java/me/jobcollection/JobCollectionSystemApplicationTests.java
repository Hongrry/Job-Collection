package me.jobcollection;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.upyun.UpException;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.service.EmailService;
import me.jobcollection.modules.system.service.VerifyService;
import me.jobcollection.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;

@SpringBootTest
class JobCollectionSystemApplicationTests {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    VerifyService verifyService;
    @Autowired
    EmailService emailService;

    @Test
    void contextLoads() {
        redisUtils.set("TOKEN_eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzQwMzc0NTYsInVzZXJJZCI6MTE5MDExMzE4OSwiaWF0IjoxNjMzMTQ4NDIzfQ.vM1rntgiI9-w-a2IyNLNaBK6RwfCtosA4r6xab9A21c", 1);
        Object o = redisUtils.get("TOKEN_eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzQwMzc0NTYsInVzZXJJZCI6MTE5MDExMzE4OSwiaWF0IjoxNjMzMTQ4NDIzfQ.vM1rntgiI9-w-a2IyNLNaBK6RwfCtosA4r6xab9A21c");
        System.out.println(o);
    }

    @Test
    void dateTest() {
        DateTime date = DateUtil.date();
        System.out.println(System.currentTimeMillis());
        System.out.println("1633404564287");
    }

    @Test
    void mailTest() {
        EmailVo code = verifyService.sendEmail("2682402775@qq.com", "CODE_");
        emailService.send(code);
    }


}
