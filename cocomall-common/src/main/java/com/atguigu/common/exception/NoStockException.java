package com.atguigu.common.exception;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/26 16:17
 */

public class NoStockException extends RuntimeException {

    private Long skuId;

    public NoStockException(Long skuId) {
        super("商品id:" + skuId + "；没有足够的库存了");
    }

    public NoStockException(String msg) {
        super(msg);
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
