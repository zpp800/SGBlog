package com.zpp.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.User;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-03 17:14:53
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

