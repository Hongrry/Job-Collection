package me.jobcollection.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableId("course_id")
    private Long id;
    @TableField("course_name")
    private String name;
}
