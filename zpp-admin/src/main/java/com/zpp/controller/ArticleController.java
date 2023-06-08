package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.dto.AddArticleDto;
import com.zpp.domain.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/article")
@Api(tags = "文章相关接口")

public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping
    @ApiOperation(value = "新增文章",notes = "增加文章")
    @SystemLog(businessName = "新增文章")
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    @ApiOperation(value = "文章查询",notes = "文章查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "name",value = "文章标题",dataTypeClass = String.class),
            @ApiImplicitParam(name = "status",value = "文章摘要",dataTypeClass = String.class)
    })
    public ResponseResult linkList(Integer pageNum,Integer pageSize,String title,String content){
        return articleService.pageLinkList(pageNum,pageSize,title,content);

    }

}