package com.atguigu.cocomall.coupon.dao;

import com.atguigu.cocomall.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author Fei Yu
 * @email chrisfyu@hotmail.com
 * @date 2026-01-26 22:54:57
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
