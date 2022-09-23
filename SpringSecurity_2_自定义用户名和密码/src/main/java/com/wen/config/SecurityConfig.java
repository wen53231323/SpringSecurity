package com.wen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * SpringSecurity的配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 将用户名和密码设置在内存中
     */
    @Autowired
    public void config(AuthenticationManagerBuilder auth) throws Exception {
        // 在内存中配置用户，默认加密方式passwordEncoder，
        // withUser用户名、password密码、roles 此用户的角色配置、多个用户调用and()方法
        auth.inMemoryAuthentication()
                .withUser("test1").password(passwordEncoder().encode("123456")).roles("admin")
                .and()
                .withUser("test2").password(passwordEncoder().encode("123456")).roles("user");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 改变加密方式：使用SpringSecurity提供的BCryptPasswordEncoder加密工具，替换默认的PasswordEncoder
        // 使用BCrypt强哈希函数加密方案，密钥迭代次数设为10（默认即为10）
        return new BCryptPasswordEncoder(10);
    }
}
