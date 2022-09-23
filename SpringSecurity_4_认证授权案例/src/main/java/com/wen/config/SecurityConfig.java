package com.wen.config;


import com.wen.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * SpringSecurity的配置类
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)// 开启权限认证注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 注入自定义的认证过滤器
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    // 注入登出成功处理器
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    // 注入认证成功处理器
    @Autowired
    private AuthenticationSuccessHandler successHandler;

    // 注入认证失败处理器
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    // 注入认证异常处理器
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    // 注入授权异常处理器
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 改变加密方式：使用SpringSecurity提供的BCryptPasswordEncoder加密工具，替换默认的PasswordEncoder
        // 使用BCrypt强哈希函数加密方案，密钥迭代次数设为10（默认即为10）
        return new BCryptPasswordEncoder(10);
    }

    // 重写authenticationManagerBean()方法，注入 认证管理器AuthenticationManager
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 把登录接口页面放行，让用户在登录页面进行登录操作
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();// 开启跨域请求
        http.csrf().disable(); // 关闭CSRF安全协议，如果 CSRF 功能没有禁用，那么退出请求必须是 POST 方式
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// 不通过Session获取SecurityContext
        http.authorizeRequests().antMatchers("/user/login").anonymous();// 对于登录接口 允许匿名访问
        http.authorizeRequests().antMatchers("/user/test1").anonymous();// 对于登录接口 允许匿名访问
        http.authorizeRequests().antMatchers("/user/test2").anonymous();// 对于登录接口 允许匿名访问
        http.authorizeRequests().anyRequest().authenticated();// 除上面外的所有请求全部需要鉴权认证

        // 将自定义的过滤器，添加在指定过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置授权异常处理器
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        // 配置认证成功处理器
        http.formLogin().successHandler(successHandler);
        // 配置认证失败处理器
        http.formLogin().failureHandler(failureHandler);
        // 配置登出成功处理器
        http.logout().logoutSuccessHandler(logoutSuccessHandler);
        // 配置认证异常处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }
}
