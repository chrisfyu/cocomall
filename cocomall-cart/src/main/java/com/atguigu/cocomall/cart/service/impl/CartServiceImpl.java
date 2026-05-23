package com.atguigu.cocomall.cart.service.impl;

import com.atguigu.cocomall.cart.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/22 18:06
 */

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    StringRedisTemplate redisTemplate;
}
