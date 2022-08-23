package com.jia.tanhua.dubbo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.jia.tanhua.dubbo.mappers")
public class DubboApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboApplication.class,args);
    }
}
