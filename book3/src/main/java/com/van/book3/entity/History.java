package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/27 - 10:58
 */
@Data
public class History {
    private int id;
    private String openId;
    private String fileName;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
