package com.wen.service.Impl;

import com.alibaba.fastjson.JSON;
import com.wen.util.ResponseResult;
import com.wen.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义SpringSecurity认证失败处理器
 */

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");
        // 对象 —> 字符串
        String json = JSON.toJSONString(result);
        // 使用工具类将字符串渲染到客户端
        WebUtils.renderString(response,json);
    }
}
