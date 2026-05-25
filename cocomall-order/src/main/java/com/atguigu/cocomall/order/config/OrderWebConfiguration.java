package com.atguigu.cocomall.order.config;

import com.atguigu.cocomall.order.interceptor.LoginUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/25 7:32
 */

@Configuration
public class OrderWebConfiguration implements WebMvcConfigurer {

    @Autowired
    LoginUserInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }
}
