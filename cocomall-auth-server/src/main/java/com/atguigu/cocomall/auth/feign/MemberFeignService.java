package com.atguigu.cocomall.auth.feign;

import com.atguigu.cocomall.auth.vo.SocialUser;
import com.atguigu.cocomall.auth.vo.UserLoginVo;
import com.atguigu.cocomall.auth.vo.UserResgisterVo;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/18 16:10
 */

@FeignClient("cocomall-member")
public interface MemberFeignService {

    @PostMapping("/member/member/register")
    R register(@RequestBody UserResgisterVo vo);

    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);

    @PostMapping("/member/member/oauth2/login")
    R oauthLogin(@RequestBody SocialUser socialUser) throws Exception;
}
