package com.atguigu.cocomall.cart.service;

import com.atguigu.cocomall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/22 18:05
 */

public interface CartService {
    CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;
}
