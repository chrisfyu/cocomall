package com.atguigu.cocomall.member.service;

import com.atguigu.cocomall.member.exception.PhoneExistException;
import com.atguigu.cocomall.member.exception.UsernameExistException;
import com.atguigu.cocomall.member.vo.MemberLoginVo;
import com.atguigu.cocomall.member.vo.MemberRegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.cocomall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author Fei Yu
 * @email chrisfyu@hotmail.com
 * @date 2026-01-26 23:25:07
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberRegisterVo vo);

    void checkPhoneUnique(String phone) throws PhoneExistException;

    void checkUsernameUnique(String username) throws UsernameExistException;

    MemberEntity login(MemberLoginVo vo);
}

