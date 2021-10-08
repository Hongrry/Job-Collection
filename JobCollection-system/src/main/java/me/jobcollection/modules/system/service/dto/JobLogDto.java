package me.jobcollection.modules.system.service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Hongrry
 * @create 2021-10-05 16:06
 */
@Data
public class JobLogDto {
    @NotNull
    private Long jobId;
    @NotEmpty
    private String fileUrl;
}
