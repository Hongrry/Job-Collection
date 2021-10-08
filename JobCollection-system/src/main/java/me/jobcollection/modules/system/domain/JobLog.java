package me.jobcollection.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-04 15:57
 */
@Data
@TableName("job_logs")
public class JobLog {
    @TableField("log_id")
    private Long logId;
    private Long date;
    @TableField("success")
    private Boolean success;
    private String description;
    private Long userId;
    private Long jobId;
    private String address;
}
