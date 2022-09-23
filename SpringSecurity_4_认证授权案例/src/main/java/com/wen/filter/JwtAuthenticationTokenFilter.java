package com.wen.filter;

import com.wen.pojo.LoginUser;
import com.wen.util.JwtUtil;
import com.wen.util.RedisCache;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 自定义SpringSecurity认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        // TODO 从请求头中获取token
        String token = request.getHeader("token");

        //  TODO 如果token没有内容就执行放行，后续的认证过滤器会判断
        // 直接放行相当于不设置SecurityContextHolder，不设置SecurityContextHolder就没法通过认证，会被后面的filter给拦住
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        // TODO 使用jwt工具类解析token获取用户id
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        // TODO 根据用户id从Redis获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        // 如果没获取到用户，说明用户过期或未登录
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }

        // TODO 将获取的用户信息和权限信息，封装到Authentication中
        // 用户在完成登录后 Security 会将用户信息存储到SecurityContextHolder类中，之后其他流程需要得到用户信息时都是从这个类中获得
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // TODO 执行放行
        filterChain.doFilter(request, response);
    }

}

