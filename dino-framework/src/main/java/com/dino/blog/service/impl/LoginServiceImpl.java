package com.dino.blog.service.impl;

import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.vo.BlogLoginVo;
import com.dino.blog.domain.vo.UserInfoVo;
import com.dino.blog.domain.entity.LoginUser;
import com.dino.blog.domain.entity.User;
import com.dino.blog.service.LoginService;
import com.dino.blog.utils.BeanCopyUtils;
import com.dino.blog.utils.JwtUtil;
import com.dino.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.dino.blog.constants.SystemConstants.USER_LOGIN_PREFIX;

/**
 * Created 10-19-2022  11:31 AM
 * Author  Dino
 */
@Service
public class LoginServiceImpl implements LoginService {
    // 调用Spring-security的AuthenticationManager类认证用户名密码
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // authenticationManager调用UserDetailsService接口的实现类 并将查询到的结果封装到authentication
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误!");
        }
        // 获取userId生成Token(jwt)
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId.toString());

        // 将用户信息存入Redis
        redisCache.setCacheObject(USER_LOGIN_PREFIX + userId, loginUser);

        // 封装userInfo和Token返回
        UserInfoVo userInfoDto = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogLoginVo blogLoginDto = new BlogLoginVo(jwt, userInfoDto);
        return ResponseResult.okResult(blogLoginDto);
    }
}
