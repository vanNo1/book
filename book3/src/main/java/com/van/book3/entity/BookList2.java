package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/4/5 - 14:24
 */
@Data
public class BookList2 {
    private int id;
    private String openId;
    private String bookList;
    private String fileName;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
