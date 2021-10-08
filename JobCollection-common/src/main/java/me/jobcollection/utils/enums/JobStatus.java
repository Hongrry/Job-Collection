package me.jobcollection.utils.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Hongrry
 * @create 2021-10-05 10:43
 */
@Getter
public enum JobStatus {
    /**
     * CODE
     */
    NONE(0, "未提交"),
    SUBMITTED(1, "已提交"),
    EXPIRED(2, "已过期");
    private final int code;
    private final String msg;

    JobStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
