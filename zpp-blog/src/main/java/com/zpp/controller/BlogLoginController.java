package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.User;
import com.zpp.domain.service.BlogLoginService;
import com.zpp.enums.AppHttpCodeEnum;
import com.zpp.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "用户登录")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @PostMapping("/logout")
    @SystemLog(businessName = "用户登出")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}