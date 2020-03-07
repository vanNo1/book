package com.van.book3.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

/**
 * @author Van
 * @date 2020/3/7 - 12:19
 */
@Data
public class Sign {
    @TableId
    int id ;
    String openId;
    long createDt;
}
