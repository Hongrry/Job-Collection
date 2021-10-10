package me.jobcollection.modules.security.service.impl;

import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.system.domain.User;
import me.jobcollection.modules.system.service.UserService;
import me.jobcollection.modules.system.service.dto.UserDto;
import me.jobcollection.modules.system.service.impl.UserServiceImpl;
import net.sf.jsqlparser.statement.select.KSQLWindow;
import net.sf.jsqlparser.util.validation.metadata.NamedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Hongrry
 * @create 2021-08-23 18:47
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    static Map<String, JwtUserDto> userCache = new HashMap<>();

    @Override
    public JwtUserDto loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto user = userService.findUserByAccount(username);
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        List<GrantedAuthority> objects = new LinkedList<>();
        if ("1190113189".equals(username)) {
            SimpleGrantedAuthority admin = new SimpleGrantedAuthority("admin");
            objects.add(admin);
        }

        return new JwtUserDto(user, objects);
    }

}
