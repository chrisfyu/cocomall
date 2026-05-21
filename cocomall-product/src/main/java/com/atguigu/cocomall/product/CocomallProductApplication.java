package com.atguigu.cocomall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableFeignClients(basePackages = "com.atguigu.cocomall.product.feign")
@EnableDiscoveryClient
@MapperScan("com.atguigu.cocomall.product.dao")
@SpringBootApplication
public class CocomallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocomallProductApplication.class, args);
    }

}
