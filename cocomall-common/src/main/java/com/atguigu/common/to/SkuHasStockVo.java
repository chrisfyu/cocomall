package com.atguigu.common.to;

import lombok.Data;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/4/17 16:56
 */

@Data
public class SkuHasStockVo {

    private Long skuId;
    private Boolean hasStock;
}
