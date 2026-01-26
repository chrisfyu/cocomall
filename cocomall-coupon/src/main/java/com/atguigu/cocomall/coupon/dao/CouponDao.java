package com.atguigu.cocomall.coupon.dao;

import com.atguigu.cocomall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author Fei Yu
 * @email chrisfyu@hotmail.com
 * @date 2026-01-26 22:54:57
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
