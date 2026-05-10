package com.atguigu.cocomall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/10 16:37
 */

@Data
public class SkuItemSaleAttrVo {

    private Long attrId;
    private String attrName;
    private List<String> attrValues;
}
