package com.van.book3.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Van
 * @date 2020/3/31 - 19:03
 */
@Data
public class LoginDTO {
    @NotEmpty(message = "openId为空")
    private String openId;
    @NotEmpty(message = "username为空")
    private String username;
    @NotEmpty(message = "password为空")
    private String password;
}
