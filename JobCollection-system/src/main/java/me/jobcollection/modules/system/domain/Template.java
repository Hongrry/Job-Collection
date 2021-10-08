package me.jobcollection.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-05 16:25
 */
@Data
@TableName("sys_template")
public class Template {
    private Long id;
    private String template;
}
