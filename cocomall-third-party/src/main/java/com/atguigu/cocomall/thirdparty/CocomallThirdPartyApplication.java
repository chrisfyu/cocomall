package com.atguigu.cocomall.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CocomallThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocomallThirdPartyApplication.class, args);
    }

}
