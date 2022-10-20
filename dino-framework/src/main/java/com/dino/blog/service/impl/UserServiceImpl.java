package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.User;
import com.dino.blog.domain.vo.UserInfoVo;
import com.dino.blog.mapper.UserMapper;
import com.dino.blog.service.UserService;
import com.dino.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户表(User)表服务实现类
 *
 * @author dino
 * @since 2022-10-19 17:07:33
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo(Long userId) {
        User user = getById(userId);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(user, UserInfoVo.class));
    }

    @Override
    public ResponseResult updateUserInfo(UserInfoVo userInfoVo) {
        User user = getById(userInfoVo.getId());
        user.setAvatar(userInfoVo.getAvatar());
        user.setEmail(userInfoVo.getEmail());
        user.setNickName(userInfoVo.getNickName());
        user.setSex(userInfoVo.getSex());
        user.setUpdateBy(user.getId());
        user.setUpdateTime(new Date());
        updateById(user);
        return ResponseResult.okResult();
    }
}
