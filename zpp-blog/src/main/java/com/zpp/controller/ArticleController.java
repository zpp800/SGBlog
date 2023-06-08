package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.Article;
import com.zpp.domain.entity.Article;
import com.zpp.domain.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@Api(tags = "文章相关接口")
public class ArticleController {

    private final ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test(){
//        return articleService.list();
//    }

    @GetMapping("/hotArticleList")
    @ApiOperation(value = "查询热门文章",notes = "查询热门文章")
    public ResponseResult hotArticleList(){
        //查询热门文章封装成ResponseResult.返回
        ResponseResult result =  articleService.hotArticleList();
        return result;
    }
    //查询文章列表，分页查询，与分类id的查询文章列表通用
    @GetMapping("/articleList")
    @ApiOperation(value = "查询文章列表",notes = "查询文章列表，分页查询，与分类id的查询文章列表通用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "categoryId",value = "分类ID",dataTypeClass = Long.class,required = true),
    })
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询文章详情",notes = "查询文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id",dataTypeClass = Long.class,required = true),
    })
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }


    @PutMapping("/updateViewCount/{id}")
    @ApiOperation(value = "更新文章浏览量",notes = "更新文章浏览量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id",dataTypeClass = Long.class,required = true),
    })
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}