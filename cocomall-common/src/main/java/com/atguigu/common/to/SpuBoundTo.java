package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/2/11 7:53
 */

@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
