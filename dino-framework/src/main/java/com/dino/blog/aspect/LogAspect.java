package com.dino.blog.aspect;

import com.alibaba.fastjson.JSON;
import com.dino.blog.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created 10-20-2022  7:17 PM
 * Author  Dino
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.dino.blog.annotation.SystemLog)")
    public void PointCut() {
    }

    /**
     * 定义通知方法
     * 并指定切点
     */
    @Around("PointCut()")
    public Object printLog(
            // 被增强的方法的信息封装成的对象
            ProceedingJoinPoint joinPoint) throws Throwable {
        // 使目标方法正常调用
        Object ret;
        try {
            handBefore(joinPoint);
            ret = joinPoint.proceed();
            handAfter(ret);
        } finally {
            // 无论是否有异常都需要打印日志信息
            // System.lineSeparator() 获取windows或linux系统的换行符
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;
    }

    private void handBefore(ProceedingJoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        // 获取被增强的方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringType(),((MethodSignature)joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }

    private void handAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }
}
