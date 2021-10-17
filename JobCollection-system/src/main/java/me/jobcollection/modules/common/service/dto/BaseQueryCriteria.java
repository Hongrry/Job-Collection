package me.jobcollection.modules.common.service.dto;

import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-14 18:55
 */
@Data
public class BaseQueryCriteria {
    private Integer page = 1;
    private Integer pageSize = 10;
    private Integer year;
    private Integer month;
    private Integer day;
    private Long userId;
    private String keyword;
}
