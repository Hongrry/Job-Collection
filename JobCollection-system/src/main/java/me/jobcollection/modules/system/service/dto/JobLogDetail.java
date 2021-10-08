package me.jobcollection.modules.system.service.dto;

import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-06 11:32
 */
@Data
public class JobLogDetail {
    private Long logId;
    private Long date;
    private Long jobId;
    private Boolean success;
    private String description;
    private Long userId;
    private String jobName;
    private String address;
}
