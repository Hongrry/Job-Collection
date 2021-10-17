package me.jobcollection.modules.system.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Hongrry
 * @create 2021-10-04 16:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobQueryCriteria extends BaseQueryCriteria {
    private Boolean success;
    private String courseName;
}
