package com.zpp.controller;

import com.zpp.domain.ResponseResult;
import com.zpp.domain.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Api(tags = "分类相关接口")
public class CategoryController {


    private final CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @ApiOperation(value = "获取分类信息",notes = "获取分类信息")
    public ResponseResult getCategoryList(){
       return categoryService.getCategoryList();
    }
}