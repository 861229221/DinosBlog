package com.dino.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dino.blog.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author dino
 * @since 2022-10-23 10:45:38
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleById(Long id);
}

