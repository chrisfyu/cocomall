package com.atguigu.cocomall.product.dao;

import com.atguigu.cocomall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性
 * 
 * @author Fei Yu
 * @email chrisfyu@hotmail.com
 * @date 2026-01-26 19:24:34
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {
	
}
