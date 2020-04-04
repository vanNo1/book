package com.van.book3.service;

import com.van.book3.common.ServerResponse;
import com.van.book3.entity.Banner;
import com.van.book3.entity.Book;
import com.van.book3.vo.BannerVO2;

import java.util.List;

/**
 * @author Van
 * @date 2020/4/3 - 22:37
 */
public interface BannerService {
    BannerVO2 assembleBannserVO2(Book book, Banner banner);
    ServerResponse<List<BannerVO2>> generateBanner();
}
