package com.atguigu.cocomall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CocomallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocomallOrderApplication.class, args);
    }

}
