package com.sqx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(basePackages = {
        "com.sqx.domain.repository.mapper",
        "com.sqx.*.**.mapper"
})
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class SqxAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqxAdminApplication.class, args);
    }
}
