package com.atguigu.cocomall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.atguigu.cocomall.ware.dao")
@EnableFeignClients
@SpringBootApplication
public class CocomallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocomallWareApplication.class, args);
    }

}
