package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.dto.UserDto;
import com.zpp.domain.entity.User;
import com.zpp.domain.service.UserService;
import com.zpp.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "获取个人信息",notes = "登录后可以查看到个人信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    @ApiOperation(value = "更新用户信息",notes = "登录后可以更改个人信息")
    public ResponseResult updateUserInfo(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        //TODO 修改个人信息的时没有判断重复
        return userService.updateUserInfo(user);
    }
    @SystemLog(businessName = "用户注册")
    @PostMapping("/register")
    @ApiOperation(value = "用户注册",notes = "用户注册")
    public ResponseResult register(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        return userService.register(user);
    }
}