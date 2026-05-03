package com.atguigu.cocomall.search.service;

import com.atguigu.cocomall.search.vo.SearchParam;
import com.atguigu.cocomall.search.vo.SearchResult;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/2 22:20
 */

public interface MallSearchService {

    /**
     * @param param 检索的所有参数
     * @return  返回检索的结果，里面包含页面需要的所有信息
     */
    SearchResult search(SearchParam param);
}
