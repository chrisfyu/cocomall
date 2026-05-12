package com.atguigu.cocomall.product.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/10 22:00
 */

@ToString
@Data
public class AttrValueWithSkuIdVo {

    private String attrValue;
    private String skuIds;
}
