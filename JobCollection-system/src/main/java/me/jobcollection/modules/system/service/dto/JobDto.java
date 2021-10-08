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
    private Long deadline;
    private boolean enable;
    private String courseName;
    private Long templateId;
}
