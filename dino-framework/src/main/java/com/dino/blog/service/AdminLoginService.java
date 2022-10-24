package com.dino.blog.service;

import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.User;

/**
 * Created 10-19-2022  11:30 AM
 * Author  Dino
 */
public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
