package com.atguigu.cocomall.product.feign.fallback;

import com.atguigu.cocomall.product.feign.SeckillFeignService;
import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/6/2 15:33
 */

@Slf4j
@Component
public class SeckillFeignServiceFallBack implements SeckillFeignService {
    @Override
    public R getSkuSeckillInfo(Long skuId) {

        log.info("熔断方法调用...getSkuSeckillInfo");
        return R.error(BizCodeEnume.TOO_MANY_REQUEST.getCode(), BizCodeEnume.TOO_MANY_REQUEST.getMsg());
    }
}
