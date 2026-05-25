package com.atguigu.cocomall.order.web;

import com.atguigu.cocomall.order.service.OrderService;
import com.atguigu.cocomall.order.vo.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/25 7:26
 */

@Controller
public class OrderWebController {

    @Autowired
    OrderService orderService;

    @GetMapping("/toTrade")
    public String toTrade(Model model) {

        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("orderConfirmData", confirmVo);

        return "confirm";
    }
}
