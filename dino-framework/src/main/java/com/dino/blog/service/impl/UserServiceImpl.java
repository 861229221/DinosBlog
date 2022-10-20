package com.dino.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.User;
import com.dino.blog.domain.vo.UserInfoVo;
import com.dino.blog.enums.AppHttpCodeEnum;
import com.dino.blog.exception.SystemException;
import com.dino.blog.mapper.UserMapper;
import com.dino.blog.service.UserService;
import com.dino.blog.utils.BeanCopyUtils;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private UserService userService;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {
        // 表单验证
        if (StringUtils.isNullOrEmpty(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_IS_NULL);
        }
        if (StringUtils.isNullOrEmpty(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_IS_NULL);
        }
        if (StringUtils.isNullOrEmpty(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_IS_NULL);
        }
        if (StringUtils.isNullOrEmpty(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_IS_NULL);
        }
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(userNicknameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.USERNICKNAME_IS_EXIST);
        }
        String encodePassWord = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassWord);
        save(user);
        return ResponseResult.okResult();
    }

    private boolean userNicknameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        int count = count(queryWrapper);
        return count == 0 ? false : true;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        int count = count(queryWrapper);
        return count == 0 ? false : true;
    }
}
