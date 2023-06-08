package com.zpp.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.DeleteBatchByIds;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.mapper.TagMapper;
import com.zpp.domain.entity.Tag;
import com.zpp.domain.service.TagService;
import com.zpp.domain.vo.PageVo;
import com.zpp.domain.vo.TagVo;
import com.zpp.enums.AppHttpCodeEnum;
import com.zpp.exception.SystemException;
import com.zpp.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-16 18:12:40
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource TagMapper tagMapper;
    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, String name) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name),Tag::getName, name);
        Page<Tag> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Tag> tagList = page.getRecords();
        //封装数据返回
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tagList, TagVo.class);
        PageVo pageVo = new PageVo(tagVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult<TagVo> AllTag() {
        List<Tag> list = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
                //标签名称不能为空
        if(!StringUtils.hasText(tag.getName())){
            throw new SystemException(AppHttpCodeEnum.TAG_NOT_NULL);
        }       //标签名称已存在
        if(tagNameExist(tag.getName())){
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_EXIST);
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(List<String> id) {
        tagMapper.deleteBatchIds(id);
        return ResponseResult.okResult();
    }

    private boolean tagNameExist(String name) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName,name);
        //count()获取记录数，
        return count(queryWrapper)>0;
    }

}

