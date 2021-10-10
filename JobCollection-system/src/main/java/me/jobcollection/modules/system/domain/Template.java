package me.jobcollection.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-05 16:25
 */
@Data
@TableName("sys_template")
public class Template {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String template;
    private String name;
}
