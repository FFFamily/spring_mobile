package org.example.controller;

import org.example.entity.CommonResponse;
import org.example.vo.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录相关请求
 */
@RestController
@RequestMapping("/management")
public class LoginController {
    @PostMapping("/login")
    public CommonResponse<String> login(LoginRequest request){
        String userName = request.getUserName();

    }
}
