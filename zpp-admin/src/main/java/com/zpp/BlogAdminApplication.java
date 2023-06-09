package com.zpp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@MapperScan("com.zpp.domain.mapper")
@EnableOpenApi  //添加此注解接口文档即可生效
//http://localhost:8989/swagger-ui/index.html
//http://localhost:8989/doc.html
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}