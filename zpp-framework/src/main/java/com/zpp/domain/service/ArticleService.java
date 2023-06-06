package com.zpp.domain.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.Article;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-02-28 10:44:05
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

//    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);
}

