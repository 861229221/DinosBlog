package com.dino.blog.controller;

/**
 * Created 10-20-2022  2:14 PM
 * Author  Dino
 */

import com.dino.blog.annotation.SystemLog;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.User;
import com.dino.blog.domain.vo.UserInfoVo;
import com.dino.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/userInfo")
    @SystemLog(businessName = "获取用户信息")
    public ResponseResult userInfo(@PathParam("userId") Long userId){
        return userService.userInfo(userId);
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody UserInfoVo userInfoVo){
        return userService.updateUserInfo(userInfoVo);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "注册")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
