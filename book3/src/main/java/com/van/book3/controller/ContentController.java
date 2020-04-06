package com.van.book3.controller;

import com.van.book3.common.CodeMsg;
import com.van.book3.common.ServerResponse;
import com.van.book3.entity.Book;
import com.van.book3.exception.GlobalException;
import com.van.book3.serviceimpl.BookServiceImpl;
import com.van.book3.serviceimpl.ContentServiceImpl;
import com.van.book3.serviceimpl.HotBookServiceImpl;
import com.van.book3.utils.LoginUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;

/**
 * @author Van
 * @date 2020/3/18 - 10:15
 */
@RestController
@Validated
public class ContentController {
    @Resource
    private ContentServiceImpl contentService;
    @Resource
    private BookServiceImpl bookService;
    @Resource
    private HotBookServiceImpl hotBookService;

    @RequestMapping("/contents")
    public ServerResponse content(@NotEmpty String fileName, HttpSession session) {
        if (LoginUtil.isLogin(session)) {
            Book book = bookService.selectBookByFileName(fileName);
            if (book==null){
                throw new GlobalException(CodeMsg.BOOK_NOT_EXIST);
            }
            hotBookService.insert(LoginUtil.getOpenId(session), book.getTitle(), book.getFileName());
        }
        return contentService.content(fileName);
    }
}
