package com.atguigu.cocomall.order.vo;

import com.atguigu.cocomall.order.entity.OrderEntity;
import lombok.Data;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/26 8:33
 */

@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;
    private Integer code; // 0 成功 错误状态码
}
