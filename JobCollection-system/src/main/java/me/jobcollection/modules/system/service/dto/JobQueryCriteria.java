package me.jobcollection.modules.system.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Hongrry
 * @create 2021-10-04 16:05
 */
@Data
@NoArgsConstructor
public class JobQueryCriteria {
    private int page = 1;
    private int pageSize = 10;
    private Integer month;
    private Integer year;
    private Boolean success;
    private String courseName;
    private String keyword;
}
