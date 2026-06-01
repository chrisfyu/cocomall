package com.atguigu.cocomall.lseckill.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/31 14:29
 */

@Data
public class SeckillSkuVo {

    /**
     * id
     */
    private Long id;
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private BigDecimal seckillCount;
    /**
     * 每人限购数量
     */
    private BigDecimal seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;
}
