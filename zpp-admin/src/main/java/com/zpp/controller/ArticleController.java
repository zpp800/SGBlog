package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.dto.AddArticleDto;
import com.zpp.domain.dto.AddCategoryDto;
import com.zpp.domain.entity.Article;
import com.zpp.domain.service.ArticleService;
import com.zpp.domain.vo.ArticleVo;
import com.zpp.domain.vo.CategoryVo;
import com.zpp.domain.vo.LinkVo;
import com.zpp.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
            @ApiImplicitParam(name = "title",value = "文章标题",dataTypeClass = String.class),
            @ApiImplicitParam(name = "summary",value = "文章摘要",dataTypeClass = String.class)
    })
    public ResponseResult linkList(Integer pageNum,Integer pageSize,String title,String summary){
        return articleService.pageLinkList(pageNum,pageSize,title,summary);

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除文章",notes = "删除文章")
    @SystemLog(businessName = "删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "被删除文章id的集合",dataTypeClass = String.class)
    })
    public ResponseResult deleteArticle(@PathVariable List<String> id){
        return articleService.deleteArticle(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "单个文章查询",notes = "修改时需要回显文章,先查询")
    @SystemLog(businessName = "单个文章查询——修改文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章回显",dataTypeClass = String.class)
    })
    public ResponseResult<ArticleVo> selectOne(@PathVariable String id){
        Article article = articleService.getById(id);
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        return ResponseResult.okResult(articleVo);
    }

    @PutMapping
    @ApiOperation(value = "修改文章",notes = "修改文章")
    @SystemLog(businessName = "修改文章")
    public ResponseResult updateTag(@RequestBody AddArticleDto articleDto){
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        articleService.updateById(article);
        return ResponseResult.okResult("您已成功修改该文章");
    }

}