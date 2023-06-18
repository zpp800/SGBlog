package com.zpp.handler.execption;

import com.zpp.domain.ResponseResult;
import com.zpp.enums.AppHttpCodeEnum;
import com.zpp.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) throws Exception {
        //原因：全局异常处理比AccessDeniedHandler先捕获AccessDeniedException异常
        //处理办法：
        //在全局异常处向上抛出AccessDeniedException异常, 有AccessDeniedException捕获
        /*由于GlobalExceptionHandler 全局异常处理器会比 AccessDeniedHandler
        先捕获 AccessDeniedException 异常，因此当配置了 GlobalExceptionHandler 后，
        会发现 AccessDeniedHandler 失效了。*/
        if(e instanceof AccessDeniedException){
            throw e;
        }
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseResult handleException(AccessDeniedException e) {
//        String message = e.getLocalizedMessage();
//        log.error("全局异常捕获AccessDeniedException：{}", message);
//        return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(),e.getMessage());
//    }

}