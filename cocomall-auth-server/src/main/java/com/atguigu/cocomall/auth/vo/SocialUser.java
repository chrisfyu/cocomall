package com.atguigu.cocomall.auth.vo;

import lombok.Data;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/19 18:06
 */

@Data
public class SocialUser {

    private String access_token;
    private String remind_in;
    private long expires_in;
    private String uid;
    private String isRealName;
}
