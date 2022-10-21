package com.dino.blog.filter;

import com.alibaba.fastjson.JSON;
import com.dino.blog.constants.SystemConstants;
import com.dino.blog.domain.ResponseResult;
import com.dino.blog.domain.entity.LoginUser;
import com.dino.blog.enums.AppHttpCodeEnum;
import com.dino.blog.utils.JwtUtil;
import com.dino.blog.utils.RedisCache;
import com.dino.blog.utils.WebUtils;
import com.qiniu.util.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Created 10-19-2022  1:52 PM
 * Author  Dino
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 获取请求头中的Token
            String token = request.getHeader("token");
            if(StringUtils.isNullOrEmpty(token)){
                // 该接口无需登陆，可以直接放行
                filterChain.doFilter(request,response);
                return;
            }
            // 解析获取userId
            Claims claims = JwtUtil.parseJWT(token);
            String userId = claims.getSubject();
            // 从redis中获取用户信息
            LoginUser loginUser = redisCache.getCacheObject(SystemConstants.USER_ADMIN_LOGIN_PREFIX + userId);
            if(Objects.isNull(loginUser)){
                // 登陆过期，提示重新登陆
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }
            // 存入SecurityContextHolder中
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
    }
}
