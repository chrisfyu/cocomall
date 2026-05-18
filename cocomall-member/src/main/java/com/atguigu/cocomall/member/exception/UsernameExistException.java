package com.atguigu.cocomall.member.exception;

/**
 * @Description:
 * @Author: Fei Yu
 * @CreateTime: 2026/5/18 11:19
 */

public class UsernameExistException extends RuntimeException {

    public UsernameExistException() {
        super("用户名已存在");
    }
}
