package com.atguigu.cocomall.order.vo;

import lombok.Data;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/30 7:26
 */

@Data
public class PayVo {

    private String out_trade_no; // 商户订单号 必填
    private String subject; // 订单名称 必填
    private String total_amount;  // 付款金额 必填
    private String body; // 商品描述 可空
}
