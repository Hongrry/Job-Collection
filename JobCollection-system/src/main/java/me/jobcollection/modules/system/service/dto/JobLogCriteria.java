package me.jobcollection.modules.system.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author Hongrry
 * @create 2021-10-04 17:17
 */
@Data
@AllArgsConstructor
public class JobLogCriteria {
    private Set<Long> jobIds;
    private Long userId;
}
