package com.zpp.exception;

import com.zpp.enums.AppHttpCodeEnum;

public class SystemException extends RuntimeException{

    private final int code;

    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 接受自定义的枚举，赋值给异常对象的code msg
     * 例如 /login 接口
     * @param httpCodeEnum 自定义枚举
     */
    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
    
}