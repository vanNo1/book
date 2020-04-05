package com.van.book3.entity;

import lombok.Data;

/**
 * @author Van
 * @date 2020/4/5 - 14:40
 */
@Data
public class BookListLike {
    private int id;
    private String openId;
    private String bookList;
    private int liked;
}

