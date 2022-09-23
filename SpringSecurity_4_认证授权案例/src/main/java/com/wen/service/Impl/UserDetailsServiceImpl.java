package com.wen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.mapper.PermissionsMapper;
import com.wen.mapper.UserMapper;
import com.wen.pojo.LoginUser;
import com.wen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionsMapper permissionsMapper;

    /**
     * 从数据库校验用户是否存在
     * 验证身份就是UserDetails接口类，交给 认证管理器 进行匹配
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
        // ArrayList<String> list = new ArrayList<>(Arrays.asList("select", "insert"));
        List<String> permissionsList = permissionsMapper.selectPermsByUserId(user.getId());

        // TODO 用户存在则返回用户信息和权限信息列表
        // 返回用户信息和权限信息列表（LoginUser实现了UserDetails接口）
        LoginUser loginUser = new LoginUser(user, permissionsList);

        return loginUser;
    }
}
