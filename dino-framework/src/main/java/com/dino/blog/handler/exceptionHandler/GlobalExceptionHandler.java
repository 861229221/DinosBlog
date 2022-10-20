package com.dino.blog.handler.exceptionHandler;

import com.dino.blog.domain.ResponseResult;
import com.dino.blog.enums.AppHttpCodeEnum;
import com.dino.blog.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Created 10-19-2022  2:45 PM
 * Author  Dino
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult sysExceptionHandler(SystemException e) {
        // 打印异常信息
        log.error("出现异常", e);
        // 从异常对象中获取提示信息返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        // 打印异常信息
        log.error("出现异常", e);
        // 从异常对象中获取提示信息返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
