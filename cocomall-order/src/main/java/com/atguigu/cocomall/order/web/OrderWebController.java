package com.atguigu.cocomall.order.web;

import com.atguigu.cocomall.order.service.OrderService;
import com.atguigu.cocomall.order.vo.OrderConfirmVo;
import com.atguigu.cocomall.order.vo.OrderSubmitVo;
import com.atguigu.cocomall.order.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/25 7:26
 */

@Controller
public class OrderWebController {

    @Autowired
    OrderService orderService;

    /**
     * 去结算确认页
     * @param model
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {

        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("orderConfirmData", confirmVo);

        return "confirm";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo) {

        SubmitOrderResponseVo responseVo = orderService.submitOrder(vo);

        //下单失败回到订单确认页重新确定订单信息
        System.out.println("订单提交的数据..." + vo);
        if (responseVo.getCode() == 0) {
            //下单成功来到支付选择页
            return "pay";
        } else {
            return "redirect:http://order.cocomall.com/toTrade";
        }
    }
}
