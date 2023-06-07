package com.zpp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@MapperScan("com.zpp.domain.mapper")
@EnableScheduling // 定时任务
@EnableOpenApi  //添加此注解接口文档即可生效
//http://localhost:7777/swagger-ui/index.html
//http://localhost:7777/doc.html
public class ZppBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZppBlogApplication.class,args);
        System.out.println(" ______  _____   _____  ");
        System.out.println("|___  / |  _  \\ |  _  \\ ");
        System.out.println("   / /  | |_| | | |_| | ");
        System.out.println("  / /   |  ___/ |  ___/ ");
        System.out.println(" / /__  | |     | |     ");
        System.out.println("/_____| |_|     |_|     1.0");
    }}
