package com.atguigu.cocomall.order.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/24 10:39
 */

@Configuration
public class MyRabbitConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("Confirm..." + correlationData + "=====" + ack + "======" + cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("Fail Message" + message + "===" + replyCode + "===" + replyText + "===" + exchange + "===" + routingKey);
            }
        });
    }

}
