package com.atguigu.cocomall.order.feign;

import com.atguigu.cocomall.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/25 10:04
 */

@FeignClient("cocomall-cart")
public interface CartFeignService {

    @GetMapping("/currentUserCartItem")
    List<OrderItemVo> getCurrentUserCartItems();


}
