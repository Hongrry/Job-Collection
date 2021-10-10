package me.jobcollection.modules.system.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jobcollection.config.EmailConfigProperties;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.service.EmailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HONGRRY
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailConfigProperties properties;

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo) {

        // 封装
        MailAccount account = new MailAccount();
        // 设置用户
        // 腾讯邮箱要求 user 和 from 相同 该相同默认的 user 是如上格式
        account.setUser(properties.getFromUser());
        account.setHost(properties.getHost());
        account.setPort(Integer.parseInt(properties.getPort()));
        account.setAuth(true);
        account.setSocketFactoryFallback(true);
        account.setPass(properties.getPass());
        account.setFrom(properties.getUser() + "<" + properties.getFromUser() + ">");
        // ssl方式发送
        account.setSslEnable(true);
        // 使用STARTTLS安全连接
        account.setStarttlsEnable(true);
        String content = emailVo.getContent();
        // 发送
        try {
            log.info("success");
            int size = emailVo.getTos().size();
            Mail.create(account)
                    .setTos(emailVo.getTos().toArray(new String[size]))
                    .setTitle(emailVo.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
