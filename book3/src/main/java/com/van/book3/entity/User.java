package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/7 - 14:06
 */
@Data
public class User {
    @TableId
    private int id;
    @NotEmpty(message = "username不能为空")
    private String username;
    @NotEmpty(message = "password不能为空")
    private String password;
    @NotEmpty(message = "openId不能为空")
    private String openId;
    @NotEmpty(message = "question不能为空")
    private String question;
    @NotEmpty(message = "answer不能为空")
    private String answer;
    private String avatarUrl;
    private String city;
    private String country;
    private int gender;
    private String language;
    private String nickName;
    private String province;
    private String platform;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
