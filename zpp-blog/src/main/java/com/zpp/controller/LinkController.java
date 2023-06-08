package com.zpp.controller;

import com.zpp.domain.ResponseResult;
import com.zpp.domain.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Api(tags = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @ApiOperation(value = "查询友链",notes = "查询所有友链")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}