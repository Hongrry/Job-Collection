package me.jobcollection.modules.system.domain.vo;

import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-04 17:04
 */
@Data
public class JobVo {
    private Long jobId;
    private String jobName;
    private Long beginTime;
    private Long deadline;
    private String desc;
    private Integer status;
    private String courseName;
}
