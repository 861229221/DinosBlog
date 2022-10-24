package com.dino.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dino.blog.domain.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author dino
 * @since 2022-10-23 10:31:26
 */

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long UserId);

    List<Menu> selectAllMenu();

    List<Menu> selectRoutersMenuByUserId(Long userId);
}

