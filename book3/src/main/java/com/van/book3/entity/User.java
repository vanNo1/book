package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/7 - 14:06
 */
@Data
public class User {
    @TableId
    private int id;
    private String openId;
    private String avatarUrl;
    private String city;
    private String country;
    private int gender;
    private String language;
    private String nickName;
    private String province;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private String platform;

}
