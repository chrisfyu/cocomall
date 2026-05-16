package com.atguigu.cocomall.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/12 22:58
 */

@Configuration
public class CocomallWebConfig implements WebMvcConfigurer {

    /**
     * 视图映射
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/reg.html").setViewName("reg");
    }
}
