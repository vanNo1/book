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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;

/**
 * @author Van
 * @date 2020/3/16 - 13:07
 */
@Validated
@RestController
@Slf4j
@RequestMapping("/shelf")
public class ShelfController {
    @Resource
    private ShelfServiceImpl shelfService;

    @RequestMapping("/get")
    public ServerResponse get(String fileName,HttpSession session) {
        //need login
        String openId = (String) session.getAttribute(Const.CURRENT_USER);
        return shelfService.get(fileName,openId);
    }

    @RequestMapping("/save")
    public ServerResponse save(@NotEmpty String shelfName, HttpSession session) {
        //need login
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
            return shelfService.save(shelfName, openId);

    }

    @RequestMapping("/remove")
    public ServerResponse remove(@NotEmpty String shelfName, HttpSession session) {
     //need login
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
            return shelfService.remove(shelfName, openId);
    }
}
