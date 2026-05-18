package com.atguigu.cocomall.auth.feign;

import com.atguigu.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/17 12:13
 */

@FeignClient("cocomall-third-party")
public interface ThirdPartyFeignService {


    @PostMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
