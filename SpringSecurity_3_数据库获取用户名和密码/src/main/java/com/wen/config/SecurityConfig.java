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
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 改变加密方式：使用SpringSecurity提供的BCryptPasswordEncoder加密工具，替换默认的PasswordEncoder
        // 使用BCrypt强哈希函数加密方案，密钥迭代次数设为10（默认即为10）
        return new BCryptPasswordEncoder(10);
    }
}
