package com.atguigu.cocomall.ware.vo;

import lombok.Data;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/26 15:30
 */

@Data
public class LockStockResult {

    private Long skuId;
    private Integer num;
    private Boolean locked;
}
