package com.zpp.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-02 08:17:49
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

