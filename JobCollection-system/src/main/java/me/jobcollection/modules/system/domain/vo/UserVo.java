package me.jobcollection.modules.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hongrry
 * @create 2021-10-02 10:13
 */
@Data
@AllArgsConstructor
public class UserVo {
    private String username;
    private String nickname;
    private String email;
    private String avatar;
}
