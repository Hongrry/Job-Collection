package me.jobcollection.modules.system.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jobcollection.modules.common.service.dto.BaseQueryCriteria;

/**
 * @author Hongrry
 * @create 2021-10-14 16:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseQueryCriteria extends BaseQueryCriteria {
    private String teacher;
}
