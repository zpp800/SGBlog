package com.zpp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 */
@Retention(RetentionPolicy.RUNTIME)
//原注解 保持在运行阶段
@Target({ElementType.METHOD})
//自定义注解运行加在方法上面
public @interface SystemLog {
    //业务名称，或者接口名称
    String businessName();
}
