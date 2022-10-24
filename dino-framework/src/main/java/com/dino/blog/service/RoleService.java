package com.dino.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dino.blog.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author dino
 * @since 2022-10-23 10:45:40
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleById(Long id);
}
