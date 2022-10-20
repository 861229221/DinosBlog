package com.dino.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.User;
import com.dino.blog.domain.vo.UserInfoVo;


/**
 * 用户表(User)表服务接口
 *
 * @author dino
 * @since 2022-10-19 17:07:32
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo(Long userId);

    ResponseResult updateUserInfo(UserInfoVo userInfoVo);

    ResponseResult register(User user);
}
