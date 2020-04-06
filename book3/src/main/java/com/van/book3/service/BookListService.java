package com.van.book3.service;

import com.van.book3.common.ServerResponse;
import com.van.book3.entity.BookList;

import javax.validation.constraints.NotEmpty;

/**
 * @author Van
 * @date 2020/4/5 - 14:26
 */
public interface BookListService {
    ServerResponse deleteBookListByName(String openId,String bookList);
    BookList selectBookListByName(String bookList);
    void addLike(String  bookList);
    void cancelLike(String  bookList);
    ServerResponse createBookList(BookList bookList);

}
