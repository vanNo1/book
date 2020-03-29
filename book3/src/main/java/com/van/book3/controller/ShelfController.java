package com.van.book3.controller;

import com.google.gson.Gson;
import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.entity.Shelf;
import com.van.book3.entity.Sign;
import com.van.book3.entity.User;
import com.van.book3.serviceimpl.ShelfServiceImpl;
import com.van.book3.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;

/**
 * @author Van
 * @date 2020/3/16 - 13:07
 */
@RestController
@Slf4j
@RequestMapping("/shelf")
public class ShelfController {
    @Resource
    private ShelfServiceImpl shelfService;

    @RequestMapping("/get")
    public ServerResponse get(HttpSession session) {
        if (!LoginUtil.isLogin(session)) {
            return ServerResponse.error("用户未登录");
        }
        String openId = LoginUtil.getOpenId(session);
        return shelfService.get(openId);
    }

    @RequestMapping("/save")
    public ServerResponse save(String shelf, HttpSession session) {
        if (!LoginUtil.isLogin(session)) {
            return ServerResponse.error("用户未登录");
        }
        Gson gson = new Gson();
        try {
            String decodeStr = URLDecoder.decode(shelf, "utf-8");
            Shelf shelfItem = gson.fromJson(decodeStr, Shelf.class);
            if (shelfItem == null) {
                return ServerResponse.error("参数有误");
            }
            return shelfService.save(shelfItem.getFileName(), shelfItem.getOpenId());

        } catch (UnsupportedEncodingException e) {
            log.error("解码错误", e);
            return ServerResponse.error("解码错误");
        }


    }

    @RequestMapping("/remove")
    public ServerResponse remove(String shelf, HttpSession session) {
        if (!LoginUtil.isLogin(session)) {
            return ServerResponse.error("用户未登录");
        }
        Gson gson = new Gson();
        try {
            String decodeStr = URLDecoder.decode(shelf, "utf-8");
            Shelf shelfItem = gson.fromJson(decodeStr, Shelf.class);
            if (shelfItem == null) {
                return ServerResponse.error("参数有误");
            }
            return shelfService.remove(shelfItem.getFileName(), shelfItem.getOpenId());

        } catch (UnsupportedEncodingException e) {
            log.error("解码错误", e);
            return ServerResponse.error("解码错误");
        }

    }
}
