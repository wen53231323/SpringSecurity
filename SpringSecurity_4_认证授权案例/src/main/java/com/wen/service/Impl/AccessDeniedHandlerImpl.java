package com.wen.service.Impl;

import com.alibaba.fastjson.JSON;
import com.wen.util.ResponseResult;
import com.wen.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义SpringSecurity授权失败处理器
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "权限不足");
        // 对象 —> 字符串
        String json = JSON.toJSONString(result);
        // 使用工具类将字符串渲染到客户端
        WebUtils.renderString(response, json);
    }
}
