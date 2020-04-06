package com.van.book3.service;

import com.van.book3.common.ServerResponse;

/**
 * @author Van
 * @date 2020/4/5 - 14:27
 */
public interface BookList2Service {
    ServerResponse addBook(String bookList, String fileName, String openId);
    ServerResponse deleteBook(String bookList,String fileName,String openId);
    ServerResponse showBookList(String bookList,int current,int pageSize);

}
