package com.atguigu.cocomall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.cocomall.search.config.CocomallElasticSearchConfig;
import com.atguigu.cocomall.search.constant.EsConstant;
import com.atguigu.cocomall.search.service.ProductSaveService;
import com.atguigu.common.es.SkuEsModel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/4/17 21:58
 */

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {

        //1.在es中建立索引，建立号映射关系（doc/json/product-mapping.json）
        //2. 在ES中保存这些数据
        BulkRequest bulkRequst = new BulkRequest();
        for (SkuEsModel model: skuEsModels){
            //1、构造保存请求
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s = JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);

            bulkRequst.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequst, CocomallElasticSearchConfig.COMMON_OPTIONS);

        //TODO 1、如果批量错误
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架完成：{}",collect);

        return b;
    }
}
