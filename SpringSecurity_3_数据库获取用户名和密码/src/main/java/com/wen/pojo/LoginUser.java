package com.wen.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 封装UserDetails类型的返回值，用来与前端传递的信息比较（登录验证）
 */
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class LoginUser implements UserDetails {
    private User user;

    // 返回权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return null;
    }

    // 获取密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 获取用户名
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    // 用户是否没过期（true未过期、false过期）
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 用户是否没被锁定（true未锁定、false锁定）
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 用户密码是否没有过期（true未过期、false过期）
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 用户是否可用（true表示启用、false表示已停用）
    @Override
    public boolean isEnabled() {
        return true;
    }
}
