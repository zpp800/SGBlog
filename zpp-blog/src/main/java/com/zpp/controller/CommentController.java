package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.constants.SystemConstants;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.dto.AddCommentDto;
import com.zpp.domain.entity.Comment;
import com.zpp.domain.service.CommentService;
import com.zpp.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论相关接口")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     *查询文章评论
     */
    @GetMapping("/commentList")
    @ApiOperation(value = "查询文章评论",notes = "查询一页文章评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章ID",dataTypeClass = Long.class,required = true),
            @ApiImplicitParam(name = "pageNum",value = "页号",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataTypeClass = Integer.class,required = true)
    })
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }
    /**
     * 添加评论，请求路径/comment，已经在类上添加，方法无需写。
     */
    @SystemLog(businessName = "添加评论")
    @PostMapping
    @ApiOperation(value = "添加评论",notes = "向文章或者友链添加评论,在dto中会有类型")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }
    /**
     *查询友链评论
     */

    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号",dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataTypeClass = Integer.class,required = true)
    })
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}