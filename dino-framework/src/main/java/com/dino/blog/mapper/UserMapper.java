package com.dino.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dino.blog.domain.entity.User;
import org.springframework.stereotype.Repository;


/**
 * 用户表(User)表数据库访问层
 *
 * @author dino
 * @since 2022-10-19 11:10:12
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}

