package com.atguigu.cocomall.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/25 8:08
 */

@Data
public class OrderConfirmVo {

    /** 会员收获地址列表 **/
    List<MemberAddressVo> address;

    /** 所有选中的购物项 **/
    List<OrderItemVo> items;

    /** 优惠券信息 **/
    Integer integration;

    // 订单总额
    BigDecimal total;

    // 应付价格
    BigDecimal payPrice;

}
