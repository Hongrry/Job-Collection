package me.jobcollection.modules.system.service;

import me.jobcollection.modules.system.domain.vo.EmailVo;

/**
 * @author HONGRRY
 */
public interface EmailService {

    /**
     *
     * @param emailVo
     */
    void send(EmailVo emailVo);
}