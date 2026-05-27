package com.atguigu.cocomall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/26 15:23
 */

@Data
public class WareSkuLockVo {

    private String orderSn;

    /** 需要锁住的所有库存信息 **/
    private List<OrderItemVo> locks;

}
