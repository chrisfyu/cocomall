package com.atguigu.cocomall.member.exception;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/18 11:19
 */

public class PhoneExistException extends RuntimeException{

    public PhoneExistException() {
        super("手机号已存在");
    }
}
