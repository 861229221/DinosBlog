package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.domain.entity.Role;
import com.dino.blog.mapper.RoleMapper;
import com.dino.blog.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author dino
 * @since 2022-10-23 10:45:40
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleById(Long id) {
        List<String> roleKeys = new ArrayList<>();
        // 判断是否是管理员
        if(id.equals(1L)){
            roleKeys.add("admin");
            return roleKeys;
        }
        // 否则返回用户对应的角色信息
        return getBaseMapper().selectRoleById(id);
    }
}
