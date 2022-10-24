package com.dino.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dino.blog.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author dino
 * @since 2022-10-23 10:31:27
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRoutersMenuByUserId(Long userId);
}
