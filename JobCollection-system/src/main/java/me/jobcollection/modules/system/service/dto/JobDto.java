package me.jobcollection.modules.system.service.dto;

import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-04 16:19
 */
@Data
public class JobDto {
    private Long jobId;
    private String name;
    private Long beginTime;
    private Long deadline;
    private Long courseId;
    private String courseName;
    private Long templateId;
}
