package me.jobcollection.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-01 12:04
 */
@Data
@TableName("sys_user")
public class User {
    @TableId(value = "user_id", type = IdType.NONE)
    private Long id;
    @TableField("account")
    private String username;
    private String nickname;
    private String email;
    private String password;
}
