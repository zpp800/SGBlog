package com.zpp.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.Tag;
import com.zpp.domain.vo.PageVo;
import com.zpp.domain.vo.TagVo;

import java.util.List;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-16 18:12:40
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, String name);

    ResponseResult<TagVo> AllTag();

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(List<String> id);
}

