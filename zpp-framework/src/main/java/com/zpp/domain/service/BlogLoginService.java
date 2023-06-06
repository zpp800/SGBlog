package com.zpp.domain.service;

import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
