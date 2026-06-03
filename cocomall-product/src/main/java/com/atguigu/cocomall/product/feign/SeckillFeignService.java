package com.atguigu.cocomall.product.feign;

import com.atguigu.cocomall.product.feign.fallback.SeckillFeignServiceFallBack;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/6/1 8:55
 */

@FeignClient(value = "cocomall-seckill", fallback = SeckillFeignServiceFallBack.class)
public interface SeckillFeignService {

    @GetMapping("/sku/seckill/{skuId}")
    R getSkuSeckillInfo(@PathVariable("skuId") Long skuId);
}
