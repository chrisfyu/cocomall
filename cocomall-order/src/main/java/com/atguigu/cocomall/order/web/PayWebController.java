package com.atguigu.cocomall.order.web;

import com.alipay.api.AlipayApiException;
import com.atguigu.cocomall.order.config.AlipayTemplate;
import com.atguigu.cocomall.order.service.OrderService;
import com.atguigu.cocomall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/30 7:44
 */

@Controller
public class PayWebController {

    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    OrderService orderService;

    /**
     * 用户下单:支付宝支付
     * 1、将支付页让浏览器展示
     * 2、支付成功以后，跳转到用户的订单列表页
     * @param orderSn
     * @return
     * @throws AlipayApiException
     */
    @ResponseBody
    @GetMapping(value = "/payOrder", produces = "text/html")
    public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {

        PayVo payVo = orderService.getOrderPay(orderSn);
        String pay = alipayTemplate.pay(payVo);
        System.out.println(pay);
        return pay;
    }
}
