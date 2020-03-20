package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/18 - 10:11
 */
@Data
public class Contents {
    @TableId
    private int id;
    private String fileName;
    private String contentName;
    private String href;
    @TableField("`order`")
    private Integer order;
    @TableField("`level`")
    private Integer level;
    private String text;
    private String label;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
