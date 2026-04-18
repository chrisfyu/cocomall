package com.atguigu.cocomall.search.service;

import com.atguigu.common.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/4/17 21:53
 */

public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
