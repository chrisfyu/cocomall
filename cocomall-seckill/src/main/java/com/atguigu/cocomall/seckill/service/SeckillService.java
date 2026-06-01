package com.atguigu.cocomall.seckill.service;

import com.atguigu.cocomall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/31 11:21
 */

public interface SeckillService {
    void uploadSeckillSkuLatest3Days();

    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    SeckillSkuRedisTo getSkuSeckillInfo(Long skuId);
}
