package com.van.mall.common;

import lombok.Data;

/**
 * @author Van
 * @date 2020/3/24 - 10:55
 */
@Data
public class ZeroException extends RuntimeException {
    private String message;
    private int code;

    public ZeroException(String message, int code) {
        super(message);
        this.code = code;
    }
}
