package me.jobcollection.modules.system.service.dto;

import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-14 16:18
 */
@Data
public class CourseQueryCriteria extends BaseQueryCriteria {
    private String teacher;
}
