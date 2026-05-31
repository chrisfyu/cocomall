package com.atguigu.cocomall.member.web;

import com.atguigu.cocomall.member.feign.OrderFeignService;
import com.atguigu.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/30 9:44
 */

@Controller
public class MemberWebController {

    @Autowired
    OrderFeignService orderFeignService;

    @GetMapping("/memberOrder.html")
    public String memberOrderPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  Model model) {
        Map<String, Object> page = new HashMap<>();
        page.put("page", pageNum.toString());

        R r = orderFeignService.listWithItem(page);
        model.addAttribute("orders", r);

        return "orderList";
    }
}
