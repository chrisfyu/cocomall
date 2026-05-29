package com.atguigu.cocomall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.cocomall.ware.entity.WareOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author Fei Yu
 * @email chrisfyu@hotmail.com
 * @date 2026-01-26 23:56:18
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);

    WareOrderTaskEntity getOrderTaskByOrderSn(String orderSn);
}

