package com.atguigu.cocomall.seckill.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/31 11:28
 */

@FeignClient("cocomall-coupon")
public interface CouponFeignService {

    @GetMapping("/coupon/seckillsession/latest3DaySession")
    R getLatest3DaySession();
}
