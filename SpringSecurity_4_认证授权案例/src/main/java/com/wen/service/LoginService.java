package com.wen.service;

import com.wen.pojo.User;
import com.wen.util.ResponseResult;

public interface LoginService {

    // 登录接口
    ResponseResult login(User user);

    // 退出登录接口
    ResponseResult logout();
}
