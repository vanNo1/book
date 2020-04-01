package com.van.book3.service;

import com.van.book3.common.ServerResponse;
import com.van.book3.entity.Shelf;
import com.van.book3.vo.ShelfVO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Van
 * @date 2020/3/16 - 13:05
 */
public interface ShelfService {
    ServerResponse<List<ShelfVO>> get(String fileName,String openId);

    ServerResponse save(String fileName, String openId);

    ServerResponse remove(String fileName, String openId);

    Integer findPeopleNum(String fileName);

    List<Shelf> getAllShelf(String fileName);

    int getShelfNumber(String openId);
}
