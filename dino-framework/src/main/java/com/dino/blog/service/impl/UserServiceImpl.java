package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.domain.entity.User;
import com.dino.blog.mapper.UserMapper;
import com.dino.blog.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author dino
 * @since 2022-10-19 17:07:33
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
