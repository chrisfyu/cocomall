package com.atguigu.cocomall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableRabbit
@EnableDiscoveryClient
@SpringBootApplication
public class CocomallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocomallOrderApplication.class, args);
    }

}
