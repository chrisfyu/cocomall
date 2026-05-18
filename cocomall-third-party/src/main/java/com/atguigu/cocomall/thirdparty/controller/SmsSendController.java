package com.atguigu.cocomall.thirdparty.controller;

import com.atguigu.cocomall.thirdparty.component.SmsComponent;
import com.atguigu.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/17 12:03
 */

@RestController
@RequestMapping("/sms")
public class SmsSendController {

    @Autowired
    SmsComponent smsComponent;

    /**
     *
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/sendcode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        smsComponent.sendSmsCode(phone, code);
        return R.ok();
    }
}
