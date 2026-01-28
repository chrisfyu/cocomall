package com.atguigu.cocomall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CocomallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocomallCouponApplication.class, args);
    }

}
