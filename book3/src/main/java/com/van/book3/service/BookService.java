package com.van.book3.service;

import com.van.book3.common.ServerResponse;
import com.van.book3.entity.Book;
import com.van.book3.vo.BookSimplyVO;

import java.util.List;

/**
 * @author Van
 * @date 2020/3/16 - 12:02
 */
public interface BookService {
    Book selectBookByFileName(String fileName);

    ServerResponse getDetail(String openId, String fileName);

    ServerResponse<List<BookSimplyVO>> recomment();

    ServerResponse<List<BookSimplyVO>> hotBook();

    ServerResponse search(String keyword, int page, int pageSize);

    ServerResponse searchList(String publisher, String category, Integer categoryId, String author,int page,int pageSize);
    ServerResponse<List<BookSimplyVO>> recommendV2(String openId);
}
