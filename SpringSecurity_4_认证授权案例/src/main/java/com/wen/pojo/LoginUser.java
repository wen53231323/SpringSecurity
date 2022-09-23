package com.wen.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public LoginUser(User user) {
        this.user = user;
    }

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    // 存储权限信息
    private List<String> permissions;

    // 存储SpringSecurity所需要的权限信息的集合
    // fastjson中的注解@JSONField：是否要把这个字段序列化成JSON字符串，默认是true
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    // 返回权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        /*
         // 方式一：for循环方式遍历
         for (String permission : permissions) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
            authorities.add(simpleGrantedAuthority);
         }
         */
        // 把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        // 方式二：stream流方式遍历
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
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
