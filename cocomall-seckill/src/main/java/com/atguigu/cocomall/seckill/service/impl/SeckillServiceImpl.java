package com.atguigu.cocomall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.cocomall.seckill.feign.CouponFeignService;
import com.atguigu.cocomall.seckill.feign.ProductFeignService;
import com.atguigu.cocomall.seckill.service.SeckillService;
import com.atguigu.cocomall.seckill.to.SeckillSkuRedisTo;
import com.atguigu.cocomall.seckill.vo.SeckillSessionsWithSkus;
import com.atguigu.cocomall.seckill.vo.SkuInfoVo;
import com.atguigu.common.utils.R;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/31 11:22
 */

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    private final String SESSIONS_CACHE_PREFIX = "seckill:session:";
    private final String SKUKILL_CACHE_PREFIX = "seckill:skus";

    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";

    @Override
    public void uploadSeckillSkuLatest3Days() {
        // 1、扫描最近三天需要参与秒杀的活动
        R session = couponFeignService.getLatest3DaySession();
        if (session.getCode() == 0) {
            // 上架商品
            List<SeckillSessionsWithSkus> sessionData = session.getData(new TypeReference<List<SeckillSessionsWithSkus>>() {
            });
            // 缓存到redis
            // 1、缓存活动信息
            saveSessionInfos(sessionData);
            // 2、缓存活动的关联商品信息
            saveSessionSkuInfos(sessionData);
        }

    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        // 1、确定当前时间属于哪个场次
        long time = new Date().getTime();

        Set<String> keys = redisTemplate.keys(SESSIONS_CACHE_PREFIX + "*");
        for (String key : keys) {
            String replace = key.replace(SESSIONS_CACHE_PREFIX, "");
            String[] s = replace.split("_");
            Long start = Long.parseLong(s[0]);
            Long end = Long.parseLong(s[1]);
            if (time >= start && time <= end) {
                // 2、获取这个场次的所有商品信息
                List<String> range = redisTemplate.opsForList().range(key, -100, 100);
                BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                List<String> list = hashOps.multiGet(range);
                if (list != null) {
                    List<SeckillSkuRedisTo> collect = list.stream().map(item -> {
                        SeckillSkuRedisTo redis = JSON.parseObject((String) item, SeckillSkuRedisTo.class);
                        return redis;
                    }).collect(Collectors.toList());
                    return collect;
                }
                break;
            }
        }

        return null;
    }

    @Override
    public SeckillSkuRedisTo getSkuSeckillInfo(Long skuId) {

        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0) {
            String regx = "\\d_" + skuId;
            for (String key : keys) {
                if (Pattern.matches(regx, key)) {
                    String json = hashOps.get(key);
                    SeckillSkuRedisTo skuRedisTo = JSON.parseObject(json, SeckillSkuRedisTo.class);

                    // 随机码的处理
                    long currentTime = new Date().getTime();
                    if (currentTime < skuRedisTo.getStartTime() || currentTime > skuRedisTo.getEndTime()) {
                        skuRedisTo.setRandomCode(null);
                    }
                    return skuRedisTo;
                }
            }
        }

        return null;
    }

    private void saveSessionInfos(List<SeckillSessionsWithSkus> sessions) {

        sessions.stream().forEach(session -> {
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            String key = SESSIONS_CACHE_PREFIX + startTime + "_" + endTime;
            Boolean hasKey = redisTemplate.hasKey(key);
            if (!hasKey) {
                List<String> collect = session.getRelationSkus().stream().map(item ->
                        item.getPromotionSessionId() + "_" + item.getSkuId().toString()).collect(Collectors.toList());
                // 缓存活动信息
                redisTemplate.opsForList().leftPushAll(key, collect);
            }
        });
    }

    private void saveSessionSkuInfos(List<SeckillSessionsWithSkus> sessions) {

        sessions.stream().forEach(session -> {
            BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
            session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                // 4、随机码
                String token = UUID.randomUUID().toString().replace("-", "");
                String redisKey = seckillSkuVo.getPromotionSessionId().toString() + "_" + seckillSkuVo.getSkuId().toString();

                if (!ops.hasKey(redisKey)) {
                    // 缓存商品
                    SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                    // 1、sku的基本数据
                    R skuInfo = productFeignService.getSkuInfo(seckillSkuVo.getSkuId());
                    if (skuInfo.getCode() == 0) {
                        SkuInfoVo info = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                        });
                        redisTo.setSkuInfo(info);
                    }

                    // 2、sku的秒杀信息
                    BeanUtils.copyProperties(seckillSkuVo, redisTo);

                    // 3、设置当前商品的秒杀时间
                    redisTo.setStartTime(session.getStartTime().getTime());
                    redisTo.setEndTime(session.getEndTime().getTime());

                    redisTo.setRandomCode(token);
                    String jsonString = JSON.toJSONString(redisTo);
                    ops.put(redisKey, jsonString);
                    // 5、使用库存作为分布式的信号量
                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                    semaphore.trySetPermits(seckillSkuVo.getSeckillCount().intValue());
                }
            });

        });
    }
}
