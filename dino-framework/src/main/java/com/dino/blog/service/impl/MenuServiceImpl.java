package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.constants.SystemConstants;
import com.dino.blog.domain.entity.Menu;
import com.dino.blog.mapper.MenuMapper;
import com.dino.blog.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author dino
 * @since 2022-10-23 10:31:28
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员，返回所有的权限
        if(id.equals(1L)){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE_C,SystemConstants.MENU_TYPE_F);
            queryWrapper.eq(Menu::getStatus,SystemConstants.MENU_STATUS_NORMAL);
            List<Menu> menuList = list(queryWrapper);
            List<String> list = menuList.stream()
                    .map(new Function<Menu, String>() {
                        @Override
                        public String apply(Menu menu) {
                            return menu.getPerms();
                        }
                    })
                    .collect(Collectors.toList());
            return list;
        }
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRoutersMenuByUserId(Long userId) {
        List<Menu> menuList  = null;
        // 判断是否是管理员
        if(userId.equals(1L)){
            // 是则返回所有符合要求的Menu
            menuList = getBaseMapper().selectAllMenu();
        }else {
            menuList = getBaseMapper().selectRoutersMenuByUserId(userId);
        }
        // 构建MenuTree形式
        List<Menu> menuTree = buildMenuTree(menuList,0L);
        return menuTree;
    }

    private List<Menu> buildMenuTree(List<Menu> menuList,Long parentId) {
        List<Menu> resultList = new ArrayList<>();
        for (Menu menu : menuList) {
            if(menu.getParentId().equals(parentId)){
                // 查询该菜单的子菜单
                List<Menu> childrenMenuList = getChildren(menu,menuList);
                menu.setChildren(childrenMenuList);
                resultList.add(menu);
            }
        }
        return resultList;
    }

    /**
     * 获取传入参数的子菜单
     * @param menu
     * @param menuList
     * @return
     */
    private List<Menu> getChildren(Menu menu,List<Menu> menuList) {
        List<Menu> childrenList = new ArrayList<>();
        for (Menu m : menuList) {
            if(m.getParentId().equals(menu.getId())){
                childrenList.add(m);
                m.setChildren(getChildren(m,menuList));
            }
        }
        return childrenList;
    }
}
