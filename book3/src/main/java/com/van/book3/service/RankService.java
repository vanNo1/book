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
    ServerResponse save(String fileName, Integer rank, String openId);

    Integer rank(String openId,String fileName);

    double rankAvg(String fileName);

    int rankNum(String fileName);

    List<Book> getHighRankBook(int rank);
    List<Book> getHighRankBookByCategory(int category,int rank,int size);
}
