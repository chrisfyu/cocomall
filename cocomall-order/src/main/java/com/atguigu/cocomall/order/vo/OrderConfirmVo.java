package com.atguigu.cocomall.order.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/25 8:08
 */

public class OrderConfirmVo {

    /** 会员收获地址列表 **/
    @Getter @Setter
    List<MemberAddressVo> address;

    /** 所有选中的购物项 **/
    @Getter @Setter
    List<OrderItemVo> items;

    /** 优惠券信息 **/
    @Getter @Setter
    Integer integration;

    @Getter @Setter
    Map<Long, Boolean> stocks;

    /** 防止重复提交的令牌 **/
    @Getter @Setter
    private String orderToken;

    public Integer getCount() {
        Integer count = 0;
        if (items != null && items.size() > 0) {
            for (OrderItemVo item : items) {
                count += item.getCount();
            }
        }
        return count;
    }

    // 订单总额
//    BigDecimal total;

    public BigDecimal getTotal() {
        BigDecimal sum = new BigDecimal("0");
        if (items != null) {
            for (OrderItemVo item : items) {
                BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                sum = sum.add(multiply);
            }
        }
        return sum;
    }

    // 应付价格
//    BigDecimal payPrice;

    public BigDecimal getPayPrice() {
        return getTotal();
    }
}
