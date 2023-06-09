package com.zpp.controller;

import com.zpp.domain.ResponseResult;
import com.zpp.domain.service.RoleService;
import com.zpp.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/user")
@Api(tags = "用户相关接口")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/list")
    @ApiOperation(value = "用户查询",notes = "用户查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "userName",value = "用户名称",dataTypeClass = String.class),
            @ApiImplicitParam(name = "phonenumber",value = "手机号",dataTypeClass = String.class),
            @ApiImplicitParam(name = "status",value = "用户状态",dataTypeClass = String.class)
    })
    public ResponseResult userList(Integer pageNum, Integer pageSize, String userName, String phonenumber,String status){
        return userService.pageLinkList(pageNum,pageSize,userName,phonenumber,status);
    }
}
