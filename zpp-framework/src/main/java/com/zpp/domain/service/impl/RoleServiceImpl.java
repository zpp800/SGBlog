package com.zpp.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.entity.Article;
import com.zpp.domain.mapper.RoleMapper;
import com.zpp.domain.entity.Role;
import com.zpp.domain.service.RoleService;
import com.zpp.domain.vo.ArticleVo;
import com.zpp.domain.vo.PageVo;
import com.zpp.domain.vo.RoleVo;
import com.zpp.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-18 15:03:49
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult pageLinkList(Integer pageNum, Integer pageSize, String roleName, String status) {
        //分页查询
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName),Role::getRoleName, roleName);
        queryWrapper.eq(StringUtils.hasText(status),Role::getStatus,status);
        queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Role> roleList = page.getRecords();
        //封装数据返回
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roleList, RoleVo.class);
        PageVo pageVo = new PageVo(roleVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}

