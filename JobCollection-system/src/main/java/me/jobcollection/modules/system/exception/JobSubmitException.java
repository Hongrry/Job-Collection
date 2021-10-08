package me.jobcollection.modules.system.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Hongrry
 * @create 2021-10-07 17:53
 */
public class JobSubmitException extends RuntimeException {
    private Long jobId;

    public JobSubmitException(String msg) {
        super(msg);
    }

    public JobSubmitException(Long jobId, String msg) {
        super(msg);
        this.jobId = jobId;
    }

    public Long getJobId() {
        return jobId;
    }
}
