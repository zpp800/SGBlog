package com.zpp.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zpp.constants.SystemConstants;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.mapper.MenuMapper;
import com.zpp.domain.entity.Menu;
import com.zpp.domain.service.MenuService;
import com.zpp.domain.vo.MenuVo;
import com.zpp.utils.BeanCopyUtils;
import com.zpp.utils.SecurityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-18 14:59:02
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    @Lazy
    private MenuService menuService;
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult getAllMenuList(String status, String menuName) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status),Menu::getStatus,status);
        wrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        wrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menuList = menuService.list(wrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                //转换成流，过滤处理后剩下一级菜单
                .filter(menu -> menu.getParentId().equals(parentId))
                //setChildren方法有返回值，开启了链式编程
                //
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                //过滤完之后就是子菜单
                .filter(m -> m.getParentId().equals(menu.getId()))
                //递归调用
                //第一行的 filter 中判断了当前菜单是否存在子菜单，如果没有子菜单，
                //就会结束递归。如果当前菜单有子菜单，就会继续执行。在第二行的 map 中，
                //继续调用 getChildren 方法，用于获取当前菜单的子菜单列表。
                //这一步会继续递归调用，直到所有菜单的子菜单都被找到为止。
                //map找子菜单的子菜单
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}

