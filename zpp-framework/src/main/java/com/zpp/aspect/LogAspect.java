package com.zpp.aspect;

import com.alibaba.fastjson.JSON;
import com.zpp.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 切面类
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    //切点为自定义注解，使用@SystemLog注解的方法都打印日志
    @Pointcut("@annotation(com.zpp.annotation.SystemLog)")
    public void pt(){

    }

    //环绕通知-通知方法
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed;
        try {
            handleBefore(pjp);
            proceed = pjp.proceed();
            handleAfter(proceed);
        } finally {
            // 结束后换行
            //System.lineSeparator()获取系统换行符
            log.info("=======End=======" + System.lineSeparator());
        }
        return proceed;
    }

    private void handleAfter(Object proceed) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(proceed));
    }

    /**
     * 响应前可打印的信息
     * @param pjp
     */
    private void handleBefore(ProceedingJoinPoint pjp) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(pjp);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(pjp.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        return methodSignature.getMethod().getAnnotation(SystemLog.class);
    }
}
