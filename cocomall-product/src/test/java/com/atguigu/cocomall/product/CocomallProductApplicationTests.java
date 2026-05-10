package com.atguigu.cocomall.product;

import com.atguigu.cocomall.product.dao.AttrGroupDao;
import com.atguigu.cocomall.product.entity.BrandEntity;
import com.atguigu.cocomall.product.service.BrandService;
import com.atguigu.cocomall.product.service.CategoryService;
import com.atguigu.cocomall.product.vo.SkuItemVo;
import com.atguigu.cocomall.product.vo.SpuItemAttrGroupVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CocomallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Test
    public void test() {
        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(1L, 225L);
        System.out.println(attrGroupWithAttrsBySpuId);
    }

    @Test
    public void redisson() {
        System.out.println(redissonClient);
    }

    @Test
    public void teststringRedisTemplate() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        ops.set("hello", "world" + UUID.randomUUID().toString());

        String hello = ops.get("hello");
        System.out.println("之前保存的数据是：" + hello);
    }

    @Test
    public void testFindPath(){
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        log.info("full path:{}", Arrays.asList(catelogPath));
    }

    @Test
    public void contextLoads() {

//        BrandEntity brandEntity = new BrandEntity();
//
//        brandEntity.setBrandId(1L);
//        brandEntity.setDescript("Apple1");
//
//        brandService.updateById(brandEntity);

//        brandEntity.setName("Apple");
//        brandService.save(brandEntity);
//        System.out.println("update successfully...");

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        list.forEach((item) -> {
            System.out.println(item);
        });

    }
}
