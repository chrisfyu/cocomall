package com.atguigu.cocomall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.atguigu.cocomall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class CocomallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocomallMemberApplication.class, args);
    }

}
