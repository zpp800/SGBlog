package com.zpp.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-18 15:03:49
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult pageLinkList(Integer pageNum, Integer pageSize, String roleName, String status);
}

