package com.atguigu.cocomall.lseckill.scheduled;

import com.atguigu.cocomall.lseckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/31 11:11
 */

/**
 * 秒杀商品定时上架
 *  每天晚上3点，上架最近三天需要三天秒杀的商品
 *  当天00:00:00 - 23:59:59
 *  明天00:00:00 - 23:59:59
 *  后天00:00:00 - 23:59:59
 */
@Slf4j
@Service
public class SeckillSkuScheduled {

    @Autowired
    SeckillService seckillService;

    @Scheduled(cron = "0 * * * * ?")
    public void uploadSeckillSkuLatest3Days() {

        //1、重复上架无需处理
        log.info("上架秒杀的商品信息");
        seckillService.uploadSeckillSkuLatest3Days();
    }
}
