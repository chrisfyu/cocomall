package com.atguigu.cocomall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.atguigu.cocomall.product.dao")
@SpringBootApplication
public class CocomallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocomallProductApplication.class, args);
    }

}
