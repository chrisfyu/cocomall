package com.atguigu.cocomall.order.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/25 16:59
 */

@FeignClient("cocomall-ware")
public interface WareFeignService {

    //查询sku是否有库存
    @PostMapping("/ware/waresku/hasstock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);
}
