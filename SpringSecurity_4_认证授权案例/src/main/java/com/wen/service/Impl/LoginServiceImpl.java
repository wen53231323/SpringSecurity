package com.wen.service.Impl;

import com.wen.pojo.LoginUser;
import com.wen.pojo.User;
import com.wen.service.LoginService;
import com.wen.util.JwtUtil;
import com.wen.util.RedisCache;
import com.wen.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisCache redisCache;

    // 通过认证管理器AuthenticationManager中的authenticate方法来进行用户认证
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {
        // TODO 将用户名和密码封装为Token，并提供给认证管理器进行认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // 通过认证管理器AuthenticationManager中的authenticate方法来进行用户认证
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // TODO 如果返回值authenticate为null，表示认证没通过，给出对应的错误提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        } else {
            // TODO 反之表示认证通过，获取用户id
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            String userId = loginUser.getUser().getId().toString();

            // TODO 将用户id作为key，把完整的用户信息存入redis
            redisCache.setCacheObject("login:" + userId, loginUser);

            // TODO 生成一个JWT，把Token响应给前端
            String jwt = JwtUtil.createJWT(userId);
            HashMap<String, String> map = new HashMap<>();
            map.put("token", jwt);
            return new ResponseResult(200, "登陆成功", map);
        }
    }

    @Override
    public ResponseResult logout() {
        // TODO 用户在完成登录后 Security 会将用户信息存储到SecurityContextHolder类中，所以可以从中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        // TODO 根据用户id将Redis中的信息删除
        redisCache.deleteObject("login:" + userid);

        return new ResponseResult(200, "退出成功");
    }
}
