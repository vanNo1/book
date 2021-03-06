package com.van.book3.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.van.book3.common.ServerResponse;
import com.van.book3.entity.HotBook;
import com.van.book3.serviceimpl.BookServiceImpl;
import com.van.book3.serviceimpl.HotBookServiceImpl;
import com.van.book3.serviceimpl.HotSearchServiceImpl;
import com.van.book3.utils.LoginUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author Van
 * @date 2020/3/17 - 16:02
 */
@RestController
public class BookController {
    @Resource
    private HotBookServiceImpl hotBookService;
    @Resource
    private BookServiceImpl bookService;
    @Resource
    private HotSearchServiceImpl hotSearchService;
    @RequestMapping("/detail")
    public ServerResponse detail(String fileName, HttpSession session){
       if (!LoginUtil.isLogin(session)){
           return ServerResponse.error("未登录");
       }
       String openId=LoginUtil.getOpenId(session);
       return bookService.getDetail(openId,fileName);
    }
    //搜索
    @RequestMapping("/search")
    public ServerResponse search(HttpSession session,String keyword, @RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "20")int pageSize){
        //is user is login then insert keyword to databse;
        if (LoginUtil.isLogin(session)){
            hotSearchService.insert(keyword,LoginUtil.getOpenId(session));
        }
    return bookService.search(keyword,page,pageSize);
    }
    @RequestMapping("/search-list")
    public ServerResponse searchList(String publisher,String author,String category,Integer categoryId,@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "20")int pageSize){
        if (publisher==null&&author==null&&category==null&&categoryId==null){
            return ServerResponse.error("参数有误");
        }
        return bookService.searchList(publisher,category,categoryId,author);
    }
    @RequestMapping("/hot-search")
    public ServerResponse hotSearch(){

        return hotBookService.hotSearch();
    }
}
