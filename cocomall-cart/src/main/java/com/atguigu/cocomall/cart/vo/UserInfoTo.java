package com.atguigu.cocomall.cart.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/22 21:41
 */

@ToString
@Data
public class UserInfoTo {

    private Long userId;
    private String userKey;

    private boolean tempUser = false;
}
