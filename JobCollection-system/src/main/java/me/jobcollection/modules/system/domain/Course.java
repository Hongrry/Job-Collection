package me.jobcollection.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-04 15:46
 */
@Data
@TableName("sys_course")
public class Course {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String teacher;
    private String template;
    private String remark;
}
