package com.atguigu.cocomall.order.controller;

import com.atguigu.cocomall.order.entity.OrderEntity;
import com.atguigu.cocomall.order.entity.OrderReturnReasonEntity;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/24 11:47
 */

@RestController
public class RabbitController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMq")
    public String sendMq(@RequestParam(value = "num", defaultValue = "10") Integer num) {

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                OrderReturnReasonEntity reasonEntity = new OrderReturnReasonEntity();
                reasonEntity.setId(1L);
                reasonEntity.setCreateTime(new Date());
                reasonEntity.setName("哈哈_" + i);
                rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java", reasonEntity,
                        new CorrelationData(UUID.randomUUID().toString()));


            } else {
                OrderEntity entity = new OrderEntity();
                entity.setOrderSn("哈哈_" + i);
                rabbitTemplate.convertAndSend("hello-java-exchange", "hello22.java", entity,
                        new CorrelationData(UUID.randomUUID().toString()));

            }
        }
        return "ok";
    }
}
