package com.wen.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.mapper.UserMapper;
import com.wen.pojo.LoginUser;
import com.wen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 从数据库校验用户是否存在
     *
     *  SpringSecurity验证身份就是验证loadUserByUsername方法的UserDetails接口类型的返回值，
     *  由 认证过滤器 判断是否与前端 输入的账号、密码、权限等信息匹配
     * */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserName,username);
        // 根据用户名查询第一个用户信息
        User user = userMapper.selectOne(lqw);
        // TODO 校验用户是否存在，不存在抛出异常，
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }

        // TODO 用户存在则返回用户信息和权限信息列表
        // 返回用户信息（LoginUser实现了UserDetails接口）
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }
}
