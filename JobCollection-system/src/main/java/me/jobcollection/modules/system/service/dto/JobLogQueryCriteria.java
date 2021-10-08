package me.jobcollection.modules.system.service.dto;

import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-07 18:32
 */
@Data
public class JobLogQueryCriteria {
    private int page = 1;
    private int pageSize = 10;
    private Integer year;
    private Integer month;
    private Integer day;
    private Boolean success;
    private String keyword;
}
