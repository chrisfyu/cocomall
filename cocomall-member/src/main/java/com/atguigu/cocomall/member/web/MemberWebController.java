package com.atguigu.cocomall.member.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/30 9:44
 */

@Controller
public class MemberWebController {

    @GetMapping("/memberOrder.html")
    public String memberOrderPage() {


        return "orderList";
    }
}
