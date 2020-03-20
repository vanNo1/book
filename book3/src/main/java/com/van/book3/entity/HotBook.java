package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/19 - 10:31
 */
//van open a book(english)
@Data
public class HotBook {
    @TableId
    private int id;
    private String openId;
    private String title;
    private String fileName;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
