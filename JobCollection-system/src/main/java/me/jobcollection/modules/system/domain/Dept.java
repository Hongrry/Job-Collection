package me.jobcollection.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-08 14:24
 */
@Data
@TableName("sys_dept")
public class Dept {
    private Long id;
    private String name;
}
