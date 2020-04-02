package com.van.book3.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Van
 * @date 2020/3/31 - 19:38
 */
@Data
public class ChangePasswordVO {
    @NotEmpty
    String username;
    @NotEmpty
    String oldPassword;
    @NotEmpty
    String newPassword;
    @NotEmpty
    String openId;
}
