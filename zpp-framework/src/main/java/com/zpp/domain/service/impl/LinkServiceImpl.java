package com.zpp.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpp.constants.SystemConstants;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.mapper.LinkMapper;
import com.zpp.domain.entity.Link;
import com.zpp.domain.service.LinkService;
import com.zpp.domain.vo.LinkVo;
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
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-02 08:17:49
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Resource
    private LinkMapper linkMapper;
    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult pageLinkList(Integer pageNum, Integer pageSize, String name, String status) {
        //分页查询
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name),Link::getName, name);
        queryWrapper.eq(StringUtils.hasText(status),Link::getStatus,status);
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Link> linkList = page.getRecords();
        //封装数据返回
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
        PageVo pageVo = new PageVo(linkVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult deleteLink(List<String> id) {
        linkMapper.deleteBatchIds(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addLink(Link link) {
        //友链名称不能为空
        if(!StringUtils.hasText(link.getName())){
            throw new SystemException(AppHttpCodeEnum.LINK_NOT_NULL);
        }       //友链地址已存在
        if(linkAddressExist(link.getAddress())){
            throw new SystemException(AppHttpCodeEnum.LINK_NAME_EXIST);
        }
        if(!StringUtils.hasText(link.getAddress())){
            throw new SystemException(AppHttpCodeEnum.LINK_ADDRESS_NOT_NULL);
        }
        //友链logo不能为空
        if(!StringUtils.hasText(link.getLogo())){
            throw new SystemException(AppHttpCodeEnum.LINK_LOGO_NOT_NULL);
        }
        //友链描述不能为空
        if(!StringUtils.hasText(link.getDescription())){
            throw new SystemException(AppHttpCodeEnum.LINK_DESCRIPTION_NOT_NULL);
        }
        //友链状态不能为空
        if(!StringUtils.hasText(link.getStatus())){
            throw new SystemException(AppHttpCodeEnum.LINK_STATUS_NOT_NULL);
        }
        save(link);
        return ResponseResult.okResult();
    }

    private boolean linkAddressExist(String address) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getAddress,address);
        //count()获取记录数，
        return count(queryWrapper)>0;
    }
}

