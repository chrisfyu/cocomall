package com.atguigu.cocomall.order.service;

import com.atguigu.cocomall.order.vo.OrderConfirmVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.cocomall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author Fei Yu
 * @email chrisfyu@hotmail.com
 * @date 2026-01-26 23:50:45
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 订单确认页返回需要用的数据
     * @return
     */
    OrderConfirmVo confirmOrder();

}

