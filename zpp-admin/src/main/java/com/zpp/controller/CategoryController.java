package com.zpp.controller;

import com.zpp.domain.ResponseResult;
import com.zpp.domain.service.CategoryService;
import com.zpp.domain.vo.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/content/category")
@Api(tags = "分类相关接口")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    
    @GetMapping("/listAllCategory")
    @ApiOperation(value = "写博文分类",notes = "写博文界面的选择分类")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    
}