/**
  * Copyright 2019 bejson.com 
  */
package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/2/11 7:56
 */

@Data
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;

}