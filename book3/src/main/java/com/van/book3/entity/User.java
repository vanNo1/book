package com.van.book3.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

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
    private long createDt;
    private long updateDt;
    private String platform;

}
