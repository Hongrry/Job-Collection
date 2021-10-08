package me.jobcollection.modules.system.service.dto;

import lombok.Data;
import me.jobcollection.modules.security.service.dto.JwtUserDto;

/**
 * @author Hongrry
 * @create 2021-10-02 13:22
 */
@Data
public class UserDto {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
}
