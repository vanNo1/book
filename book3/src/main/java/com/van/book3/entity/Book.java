package com.van.book3.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/16 - 11:55
 */
@Data
public class Book {
    @TableId
    private int id;
    private String fileName;
    private String cover;
    private String title;
    private String author;
    private String publisher;
    private Integer category;
    private String categoryText;
    private String language;
    private String rootFile;
    private String originalName;
    private String filePath;
    private String unzipPath;
    private String coverPath;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
