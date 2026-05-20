package com.atguigu.cocomall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.cocomall.auth.feign.MemberFeignService;
import com.atguigu.cocomall.auth.vo.MemberRespVo;
import com.atguigu.cocomall.auth.vo.SocialUser;
import com.atguigu.common.utils.HttpUtils;
import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/19 12:00
 */

@Slf4j
@Controller
public class OAuth2Controller {

    @Autowired
    MemberFeignService memberFeignService;

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code) throws Exception {

        // TODO 申请微博登录应用信息
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "2077705774");
        // 2636917288
        map.put("client_secret", "40af02bd1c7e435ba6a6e9cd3bf799fd");
        // 6a263e9284c6c1a74a62eadacc11b6e2
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", "http://auth.gulimall.com/oauth2.0/weibo/success");
        map.put("code", code);
        // 1、根据code换取access_token
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<>(), new HashMap<>(), map);

        if (response.getStatusLine().getStatusCode() == 200) {
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);

            //知道了哪个社交用户
            //1）、当前用户如果是第一次进网站，自动注册进来（为当前社交用户生成一个会员信息，以后这个社交账号就对应指定的会员）
            //登录或者注册这个社交用户
            R oauthLogin = memberFeignService.oauthLogin(socialUser);
            if (oauthLogin.getCode() == 0) {
                MemberRespVo data = oauthLogin.getData("data", new TypeReference<MemberRespVo>() {
                });
                log.info("登陆成功：用户：{}", data.toString());

                return "redirect:http://cocomall.com";
            } else {
                return "redirect:http://auth.cocomall.com/login.html";
            }

        } else {
            return "redirect:http://auth.cocomall.com/login.html";
        }
    }
}
