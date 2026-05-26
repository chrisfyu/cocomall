package com.atguigu.cocomall.ware.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/25 22:20
 */

@Data
public class FareVo {

    private MemberAddressVo address;
    private BigDecimal fare;
}
