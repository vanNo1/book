package com.van.book3.vo;

import lombok.Data;

/**
 * @author Van
 * @date 2020/3/6 - 19:52
 */
@Data
public class OpenIdVO<T> {
private int error_code;
private String msg;
private T data;
}
