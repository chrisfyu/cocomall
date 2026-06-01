package com.atguigu.cocomall.coupon;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;

//@SpringBootTest
public class CocomallCouponApplicationTests {

    @Test
    public void contextLoads() {

        LocalDate now = LocalDate.now();
        LocalDate plus = now.plusDays(1);
        LocalDate plus2 = now.plusDays(2);

        System.out.println(now);
        System.out.println(plus);
        System.out.println(plus2);

    }

}
