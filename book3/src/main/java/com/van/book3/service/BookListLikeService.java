package com.van.book3.service;

import com.van.book3.common.ServerResponse;

/**
 * @author Van
 * @date 2020/4/5 - 14:41
 */
public interface BookListLikeService {
    ServerResponse likeOrDislike(String openId, String bookList);
}
