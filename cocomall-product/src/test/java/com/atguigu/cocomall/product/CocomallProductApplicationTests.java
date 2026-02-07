package com.atguigu.cocomall.product;

import com.atguigu.cocomall.product.entity.BrandEntity;
import com.atguigu.cocomall.product.service.BrandService;
import com.atguigu.cocomall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CocomallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

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
