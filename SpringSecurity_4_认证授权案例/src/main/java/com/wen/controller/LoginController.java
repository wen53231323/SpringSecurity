package com.wen.controller;


import com.wen.pojo.User;
import com.wen.service.LoginService;
import com.wen.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/test1")
    public String test1() {
        return "Test1";
    }

    @GetMapping("/test2")
    // 判断用户是否有select权限，如果有则可以访问该资源
    @PreAuthorize("hasAuthority('select')")
    public String test2() {
        return "Test2";
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        // 登录
        ResponseResult result = loginService.login(user);
        return result;
    }

    @GetMapping("/logout")
    public ResponseResult login() {
        // 调用登录逻辑
        ResponseResult result = loginService.logout();
        return result;
    }
}
