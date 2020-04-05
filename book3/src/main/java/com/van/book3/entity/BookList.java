package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/4/5 - 14:23
 */
@Data
public class BookList {
    private int id;
    private String openId;
    @NotEmpty
    private String bookList;
    @NotEmpty
    private String title;
    private int likes;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
