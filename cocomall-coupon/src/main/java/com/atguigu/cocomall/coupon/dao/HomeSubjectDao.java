package com.atguigu.cocomall.coupon.dao;

import com.atguigu.cocomall.coupon.entity.HomeSubjectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 * 
 * @author Fei Yu
 * @email chrisfyu@hotmail.com
 * @date 2026-01-26 22:54:57
 */
@Mapper
public interface HomeSubjectDao extends BaseMapper<HomeSubjectEntity> {
	
}
