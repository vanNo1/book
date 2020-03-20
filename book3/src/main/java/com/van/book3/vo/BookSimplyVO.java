package com.van.book3.vo;

import lombok.Data;

/**
 * @author Van
 * @date 2020/3/18 - 11:08
 */
@Data
public class BookSimplyVO {
    private int id;
    private String fileName;
    private String cover;
    private String title;
    private String author;
    private String publisher;
    private String bookId;
    private Integer category;
    private String categoryText;
    private String language;
    private String rootFile;
}
