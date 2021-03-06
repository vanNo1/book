package com.van.book3.service;

import com.van.book3.common.ServerResponse;
import com.van.book3.entity.Book;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Van
 * @date 2020/3/17 - 12:10
 */
public interface RankService {
    ServerResponse save(String fileName, Integer rank, HttpSession session);
    Integer rank(String openId);
    double rankAvg(String fileName);
    int rankNum(String fileName);
    List<Book> getBookFromRank(int rank);
}
