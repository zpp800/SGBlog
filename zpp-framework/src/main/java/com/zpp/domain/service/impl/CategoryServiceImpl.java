package com.zpp.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpp.constants.SystemConstants;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.Article;
import com.zpp.domain.mapper.CategoryMapper;
import com.zpp.domain.entity.Category;
import com.zpp.domain.service.ArticleService;
import com.zpp.domain.service.CategoryService;
import com.zpp.domain.vo.CategoryVo;
import com.zpp.domain.vo.LinkVo;
import com.zpp.domain.vo.PageVo;
import com.zpp.enums.AppHttpCodeEnum;
import com.zpp.exception.SystemException;
import com.zpp.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-02-28 17:00:37
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ArticleService articleService;
    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                //过滤，因为需要正常状态的
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.CATEGORY_NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult pageLinkList(Integer pageNum, Integer pageSize, String name, String status) {
        //分页查询
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name),Category::getName, name);
        queryWrapper.eq(StringUtils.hasText(status),Category::getStatus,status);
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Category> categoryList = page.getRecords();
        //封装数据返回
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        PageVo pageVo = new PageVo(categoryList,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult deleteCategory(List<String> id) {
        categoryMapper.deleteBatchIds(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addLink(Category category) {
        //分类名称不能为空
        if(!StringUtils.hasText(category.getName())){
            throw new SystemException(AppHttpCodeEnum.CATEGORY_NOT_NULL);
        }
        //分类描述不能为空
        if(!StringUtils.hasText(category.getDescription())){
            throw new SystemException(AppHttpCodeEnum.CATEGORY_DESCRIPTION_NOT_NULL);
        }
        //分类名称已存在
        if(linkAddressExist(category.getName())){
            throw new SystemException(AppHttpCodeEnum.CATEGORY_NAME_EXIST);
        }
        //分类状态不能为空
        if(!StringUtils.hasText(category.getStatus())){
            throw new SystemException(AppHttpCodeEnum.CATEGORY_STATUS_NOT_NULL);
        }
        save(category);
        return ResponseResult.okResult();
    }
    private boolean linkAddressExist(String name) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName,name);
        //count()获取记录数，
        return count(queryWrapper)>0;
    }

}

