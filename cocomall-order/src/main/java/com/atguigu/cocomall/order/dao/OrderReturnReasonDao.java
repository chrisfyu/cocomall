package com.atguigu.cocomall.order.dao;

import com.atguigu.cocomall.order.entity.OrderReturnReasonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退货原因
 * 
 * @author Fei Yu
 * @email chrisfyu@hotmail.com
 * @date 2026-01-26 23:50:45
 */
@Mapper
public interface OrderReturnReasonDao extends BaseMapper<OrderReturnReasonEntity> {
	
}
