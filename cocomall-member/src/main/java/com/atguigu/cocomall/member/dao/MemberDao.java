package com.atguigu.cocomall.member.dao;

import com.atguigu.cocomall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Fei Yu
 * @email chrisfyu@hotmail.com
 * @date 2026-01-26 23:25:07
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
