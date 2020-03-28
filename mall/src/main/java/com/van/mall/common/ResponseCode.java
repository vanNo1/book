package com.van.mall.common;

import lombok.Getter;

/**
 * @author Van
 * @date 2020/3/9 - 16:42
 */
@Getter
public enum ResponseCode {
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR");
    private int code;
    private String msg;

    private ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
