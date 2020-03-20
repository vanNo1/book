package com.van.book3.controller;

import com.van.book3.common.ServerResponse;
import com.van.book3.serviceimpl.BookServiceImpl;
import com.van.book3.serviceimpl.HotSearchServiceImpl;
import com.van.book3.utils.LoginUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.rmi.ServerError;

/**
 * @author Van
 * @date 2020/3/18 - 10:37
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private BookServiceImpl bookService;
    @RequestMapping(value = {"/recommend/v2","freeRead/v2"})
    public ServerResponse recommend(){
        return bookService.recomment();
    }
    @RequestMapping("/hotBook/v2")
    public ServerResponse hotBook(){
        return bookService.hotBook();
    }
    @RequestMapping("/v2")
    public ServerResponse home(HttpSession session){
    String openId= LoginUtil.getOpenId(session);
    return bookService.getHomeData(openId);
    }

}
