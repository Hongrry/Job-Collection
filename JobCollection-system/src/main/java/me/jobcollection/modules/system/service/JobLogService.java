package me.jobcollection.modules.system.service;

import me.jobcollection.modules.system.domain.JobLog;
import me.jobcollection.modules.system.domain.vo.EmailVo;
import me.jobcollection.modules.system.domain.vo.JobLogVo;
import me.jobcollection.modules.system.domain.vo.Result;
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
     * 提交文件
     *
     * @param jobId
     * @param newPath
     */
    void addSuccessLog(Long jobId, String newPath);

    /**
     * @param jobLogQueryCriteria
     * @return
     */
    Result listJobLogDetail(JobLogQueryCriteria jobLogQueryCriteria);

    /**
     * 发送通知邮件
     *
     * @param jobId
     * @param path
     * @return
     */
    EmailVo sendEmail(Long jobId, String path);

    /**
     * 添加错误记录
     *
     * @param jobId
     * @param description
     */
    void addErrorLog(Long jobId, String description);
}
