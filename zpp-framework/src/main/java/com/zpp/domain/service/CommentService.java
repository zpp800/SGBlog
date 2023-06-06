package com.zpp.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.Comment;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 18:31:13
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

