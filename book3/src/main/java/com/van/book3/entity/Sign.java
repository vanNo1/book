package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/7 - 12:19
 */
@Data
public class Sign {
    @TableId
    int id ;
    String openId;
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
}
