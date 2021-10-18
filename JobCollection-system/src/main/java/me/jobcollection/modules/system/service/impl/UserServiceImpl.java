package me.jobcollection.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.utils.SecurityUtils;
import me.jobcollection.modules.system.domain.User;
import me.jobcollection.modules.common.exception.BadRequestException;
import me.jobcollection.modules.system.mapper.UserMapper;
import me.jobcollection.modules.system.service.UserService;
import me.jobcollection.modules.system.service.dto.UserDto;
import me.jobcollection.modules.common.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hongrry
 * @create 2021-10-01 13:02
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RedisUtils redisUtils;

    @Override
    public UserDto findUserByAccount(String account, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, account);
        if (password != null) {
            queryWrapper.eq(User::getPassword, password);
        }
        queryWrapper.select(User::getUsername, User::getId, User::getNickname, User::getPassword, User::getEmail);
        queryWrapper.last("limit 1");
        return convert(userMapper.selectOne(queryWrapper));
    }

    @Override
    public UserDto findUserByAccount(String account) {
        return findUserByAccount(account, null);
    }

    @Override
    public UserDto findUserByUserId(Long userId) {
        return findUserByAccount(String.valueOf(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserDto userDto) {
        UserDto oldInfo = SecurityUtils.getUser();
        boolean flag = false;
        User user = new User();

        // 先验证是否需要修改邮箱
        String newEmail = userDto.getEmail();
        if (StringUtils.isNotEmpty(newEmail)) {
            // 新邮箱与旧邮箱不能相同
            if (newEmail.equals(oldInfo.getEmail())) {
                throw new BadRequestException("新邮箱不能与旧邮箱相同");
            }
            user.setEmail(userDto.getEmail());
            flag = true;
        }
        String newPassword = userDto.getPassword();
        if (StringUtils.isNotEmpty(newPassword)) {
            if (oldInfo.getPassword().equals(newPassword)) {
                throw new BadRequestException("新密码不能与旧密码相同");
            }
            user.setPassword(userDto.getPassword());
            flag = true;
        }
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, userDto.getUsername());
        if (flag) {
            userMapper.update(user, updateWrapper);
        }
    }

    @Override
    public void updateUserCache(Long userId, String token) {
        UserDto userDto = findUserByUserId(userId);

        JwtUserDto jwtUserDto = (JwtUserDto) redisUtils.get("TOKEN_" + token);
        jwtUserDto.getUser().setEmail(userDto.getEmail());
        jwtUserDto.getUser().setPassword(userDto.getPassword());
        redisUtils.set("TOKEN_" + token, jwtUserDto);
    }

    private UserDto convert(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
