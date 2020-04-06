package com.van.book3.controller;

import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.entity.BookList;
import com.van.book3.serviceimpl.BookList2ServiceImpl;
import com.van.book3.serviceimpl.BookListLikeServiceImpl;
import com.van.book3.serviceimpl.BookListServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import java.security.SecureRandom;

/**
 * @author Van
 * @date 2020/4/5 - 14:28
 */
@Validated
@RestController
@RequestMapping("/bookList")
public class BookListController {
    @Resource
    private BookList2ServiceImpl bookList2Service;
    @Resource
    private BookListServiceImpl bookListService;
    @Resource
    private BookListLikeServiceImpl bookListLikeService;
    @RequestMapping("/deleteBookList")
    public ServerResponse deleteBookList(@NotEmpty String  bookList,HttpSession session){
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
        return bookListService.deleteBookListByName(openId,bookList);
    }
    @RequestMapping("/showBookList")
    public ServerResponse showBookList(@NotEmpty String bookList, @RequestParam(defaultValue = "1")int current,@RequestParam(defaultValue = "6")int pageSize){
        return bookList2Service.showBookList(bookList,current,pageSize);
    }
    @RequestMapping("/deleteBook")
    public ServerResponse deleteBook(HttpSession session,@NotEmpty String bookList,@NotEmpty String fileName){
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
      return   bookList2Service.deleteBook(bookList,fileName,openId);
    }
    @RequestMapping("/addBook")
    public ServerResponse addBook(HttpSession session, @NotEmpty String bookList,@NotEmpty String fileName){
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
        return bookList2Service.addBook(bookList,fileName,openId);
    }
    @RequestMapping("/likeOrDislike")
    public ServerResponse likeOrDislike(HttpSession session,@NotEmpty String bookList){
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
        return bookListLikeService.likeOrDislike(openId,bookList);
    }
    @RequestMapping("/create")
    public ServerResponse create(HttpSession session, @Validated BookList bookList){
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
        bookList.setOpenId(openId);
        return bookListService.createBookList(bookList);
    }

}
