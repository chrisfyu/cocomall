package com.atguigu.cocomall.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/11 23:31
 */

@ConfigurationProperties(prefix = "cocomall.thread")
@Component
@Data
public class ThreadPoolConfigProperties {
    private Integer coreSize;
    private Integer maxSize;
    private Integer keepAliveTime;
}
