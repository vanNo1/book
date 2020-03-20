package com.van.book3.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/16 - 15:27
 */
@Data
public class ShelfVO implements Serializable {
    private String fileName;
    private String openId;
    private LocalDateTime date;
    private int id;
    private String cover;
    private String title;
    private String author;
    private String publisher;
    private int bookId;
    private int category;
    private String categoryText;
    private String language;
    private String rootFile;
}
