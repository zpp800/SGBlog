package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.dto.UserDto;
import com.zpp.domain.entity.User;
import com.zpp.domain.service.BlogLoginService;
import com.zpp.enums.AppHttpCodeEnum;
import com.zpp.exception.SystemException;
import com.zpp.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录相关接口")
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "用户登录")
    @ApiOperation(value = "用户登录",notes = "判断用户是否合法")
    public ResponseResult login(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @PostMapping("/logout")
    @SystemLog(businessName = "用户登出")
    @ApiOperation(value = "用户登出",notes = "用户退出，删除redis缓存")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}