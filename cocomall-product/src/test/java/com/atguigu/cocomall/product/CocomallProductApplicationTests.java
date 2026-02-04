package com.atguigu.cocomall.product;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.cocomall.product.entity.BrandEntity;
import com.atguigu.cocomall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CocomallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    OSSClient ossClient;

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

    @Test
    public void testUpload() throws FileNotFoundException {
//        // Endpoint以东京为例，其它Region请按实际情况填写。
//        String endpoint = "oss-ap-northeast-1.aliyuncs.com";
//        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "LTAI5t9cbsLmdV2N47ov4ufH";
//        String accessKeySecret = "ByzZq1NeTfcySDOMcEZaHT7NFyvuKk";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream("C:\\Users\\yufei\\Desktop\\2.jpg");

        ossClient.putObject("cocomall-bucket", "2.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        System.out.println("上传成功...");
    }
}
