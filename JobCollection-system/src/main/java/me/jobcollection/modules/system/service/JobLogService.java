package me.jobcollection.modules.system.service;

import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.system.domain.JobLog;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.domain.vo.JobLogVo;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobLogCriteria;
import me.jobcollection.modules.system.service.dto.JobLogQueryCriteria;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-04 17:16
 */
public interface JobLogService {
    /**
     * 查询提交记录
     *
     * @param userId
     * @param jobId
     * @param isSuccess
     * @return
     */
    JobLog queryJobLog(Long userId, Long jobId, boolean isSuccess);

    /**
     * @param jobId
     * @param newPath
     * @param currentUser
     */
    void addSuccessLog(Long jobId, String newPath, JwtUserDto currentUser);

    /**
     * @param jobLogQueryCriteria
     * @return
     */
    Result listJobLogDetail(JobLogQueryCriteria jobLogQueryCriteria,JwtUserDto currentUser);

    /**
     * 发送通知邮件
     *
     * @param jobId
     * @param path
     * @return
     */
    EmailVo sendEmail(JobDto jobDto, String path, JwtUserDto currentUser);

    /**
     * 添加错误记录
     *
     * @param jobId
     * @param description
     */
    void addErrorLog(Long jobId, String description);

    /**
     * 删除作业记录
     *
     * @param jobId
     */
    void deleteLogByJobId(Long jobId);
}
