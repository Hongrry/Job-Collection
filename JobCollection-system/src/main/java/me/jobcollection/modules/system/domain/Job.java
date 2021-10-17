package me.jobcollection.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-01 12:27
 */
@Data
@TableName("sys_job")
public class Job {
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;
    @TableField("job_name")
    private String jobName;
    private Long beginTime;
    private Long deadline;
    private Long courseId;
}
