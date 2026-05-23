package com.atguigu.cocomall.cart.controller;

import com.atguigu.cocomall.cart.interceptor.CartInterceptor;
import com.atguigu.cocomall.cart.vo.UserInfoTo;
import com.atguigu.common.constant.AuthServerConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/22 21:21
 */

@Controller
public class CartController {
    /**
     * 去购物车页面的请求
     * 浏览器有一个cookie:user-key 标识用户的身份，一个月过期
     * 如果第一次使用jd的购物车功能，都会给一个临时的用户身份:
     * 浏览器以后保存，每次访问都会带上这个cookie；
     *
     * 登录：session有
     * 没登录：按照cookie里面带来user-key来做
     * 第一次，如果没有临时用户，自动创建一个临时用户
     *
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListPage() {

        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();

        return "cartList";
    }
}
