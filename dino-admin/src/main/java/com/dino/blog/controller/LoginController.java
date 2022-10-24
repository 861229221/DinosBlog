package com.dino.blog.controller;

import com.dino.blog.annotation.SystemLog;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.LoginUser;
import com.dino.blog.domain.entity.Menu;
import com.dino.blog.domain.entity.User;
import com.dino.blog.domain.vo.AdminUserInfoVo;
import com.dino.blog.domain.vo.RoutersVo;
import com.dino.blog.domain.vo.UserInfoVo;
import com.dino.blog.enums.AppHttpCodeEnum;
import com.dino.blog.exception.SystemException;
import com.dino.blog.service.AdminLoginService;
import com.dino.blog.service.MenuService;
import com.dino.blog.service.RoleService;
import com.dino.blog.utils.BeanCopyUtils;
import com.dino.blog.utils.SecurityUtil;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台登陆控制
 * Created 10-22-2022  9:30 AM
 * Author  Dino
 */
@RestController
public class LoginController {
    @Autowired
    private AdminLoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    @SystemLog(businessName = "登陆")
    public ResponseResult login(@RequestBody User user) {
        if (StringUtils.isNullOrEmpty(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getAdminUserInfo() {
        // 获取当前的登陆用户
        LoginUser loginUser = SecurityUtil.getLoginUser();
        // 获取用户的权限terms字段信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        // 查询用户角色信息
        List<String> roleKeys = roleService.selectRoleById(loginUser.getUser().getId());

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeys, BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class));
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRoutersInfo() {
        Long userId = SecurityUtil.getUserId();
        // 查询menu
        List<Menu> menuList = menuService.selectRoutersMenuByUserId(userId);
        // 封装数据返回
        RoutersVo routersVo =  new RoutersVo();
        routersVo.setMenus(menuList);
        return ResponseResult.okResult(routersVo);
    }

    @PostMapping("/user/logout")
    @SystemLog(businessName = "登出")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
