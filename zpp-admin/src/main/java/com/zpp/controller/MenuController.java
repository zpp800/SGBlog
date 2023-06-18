package com.zpp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.Menu;
import com.zpp.domain.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/menu")
@Api(tags = "菜单相关接口")
@PreAuthorize("@zpp.hasPermission('system:menu:list')")
public class MenuController {
    @Resource
    private MenuService menuService;
    @PreAuthorize("@zpp.hasPermission('system:menu:query')")
    @GetMapping("/list")
    @ApiOperation(value = "查询菜单",notes = "查询菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "菜单状态",dataTypeClass = String.class),
            @ApiImplicitParam(name = "menuName",value = "菜单名称",dataTypeClass = String.class)
    })
    public ResponseResult menuList(String status,String menuName){
        return menuService.getAllMenuList(status,menuName);
    }
}
