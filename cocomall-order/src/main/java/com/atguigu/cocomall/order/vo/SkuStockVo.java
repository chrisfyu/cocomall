package com.atguigu.cocomall.order.vo;

import lombok.Data;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/25 17:03
 */

@Data
public class SkuStockVo {

    private Long skuId;
    private Boolean hasStock;
}
