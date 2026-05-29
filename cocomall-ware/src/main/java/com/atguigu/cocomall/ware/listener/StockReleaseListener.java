package com.atguigu.cocomall.ware.listener;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.cocomall.ware.entity.WareOrderTaskDetailEntity;
import com.atguigu.cocomall.ware.entity.WareOrderTaskEntity;
import com.atguigu.cocomall.ware.service.WareSkuService;
import com.atguigu.cocomall.ware.vo.OrderVo;
import com.atguigu.common.to.mq.OrderTo;
import com.atguigu.common.to.mq.StockDetailTo;
import com.atguigu.common.to.mq.StockLockedTo;
import com.atguigu.common.utils.R;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/28 22:15
 */

@Service
@RabbitListener(queues = "stock.release.stock.queue")
public class StockReleaseListener {

    @Autowired
    WareSkuService wareSkuService;

    /**
     * 2、下单成功，库存锁定成功，后续业务调用失败，导致订单回滚。需要解锁库存。
     *
     * @param to
     * @param message
     */
    @RabbitHandler
    public void handleStockLockedRelease(StockLockedTo to, Message message, Channel channel) throws IOException {

        System.out.println("收到解锁库存的消息");
        try {
            // 当前消息是否被第二次及以后重新派发过来的
            // Boolean redelivered = message.getMessageProperties().getRedelivered();

            //解锁库存
            wareSkuService.unlockStock(to);
            // 手动删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            e.printStackTrace();
        }
    }

    @RabbitHandler
    public void handleOrderCloseRelease(OrderTo to, Message message, Channel channel) throws IOException {

        System.out.println("订单关闭准备解锁库存...");
        try {
            wareSkuService.unlockStock(to);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            e.printStackTrace();
        }
    }
}
