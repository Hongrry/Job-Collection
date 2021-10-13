package me.jobcollection.modules.system.domain.vo;

import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-06 11:24
 */
@Data
public class JobLogVo {
    private Long logId;
    private Long date;
    private Boolean success;
    private String description;
    private String courseName;
    private String jobName;
    private String address;
}
