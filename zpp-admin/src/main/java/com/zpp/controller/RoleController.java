package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.dto.AddLinkDto;
import com.zpp.domain.dto.RoleDto;
import com.zpp.domain.entity.Role;
import com.zpp.domain.service.RoleService;
import com.zpp.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/role")
@Api(tags = "角色相关接口")
public class RoleController {
    @Resource
    private RoleService roleService;
    @GetMapping("/list")
    @ApiOperation(value = "角色查询",notes = "角色查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "roleName",value = "角色名称",dataTypeClass = String.class),
            @ApiImplicitParam(name = "status",value = "角色状态",dataTypeClass = String.class)
    })
    public ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status){
        return roleService.pageLinkList(pageNum,pageSize,roleName,status);
    }

    @PutMapping("/changeStatus")
    @ApiOperation(value = "角色状态",notes = "角色状态")
    @SystemLog(businessName = "角色状态")
    public ResponseResult changeLinkStatus(@RequestBody RoleDto roleDto){
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        roleService.updateById(role);
        return ResponseResult.okResult();
    }
}
