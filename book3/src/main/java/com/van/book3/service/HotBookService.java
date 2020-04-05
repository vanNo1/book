package com.van.book3.service;

import com.van.book3.common.ServerResponse;
import com.van.book3.entity.Book;

import java.util.List;

/**
 * @author Van
 * @date 2020/3/19 - 10:34
 */
public interface HotBookService {
    int insert(String openId, String title, String fileName);

    ServerResponse hotSearch();
    int readerCount(String fileName);
    List<Book> hotBookThree(String openId);
}
