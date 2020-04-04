package com.van.book3.controller;

import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.serviceimpl.ReviewServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Van
 * @date 2020/4/4 - 11:42
 */
@Validated
@RestController
@RequestMapping("/review")
public class ReviewController {
    @Resource
    private ReviewServiceImpl reviewService;
    @RequestMapping("/insertOrUpdate")
    public ServerResponse insert(HttpSession session,  @NotEmpty String fileName, @Size(min = 10) String summary, @NotEmpty String title){
        //need to login
        String openId=(String)session.getAttribute(Const.CURRENT_USER);
        return reviewService.insertOrUpdateReview(openId,fileName,summary,title);
    }
    @RequestMapping("/list")
    public ServerResponse list(@NotEmpty String fileName, @RequestParam(defaultValue = "3") int pageSize, @RequestParam(defaultValue = "1")int current){
        return reviewService.listReview(fileName, pageSize, current);
    }
    @RequestMapping("/delete")
    public ServerResponse delete(HttpSession session,@NotEmpty String fileName){
        //need to login
        String openId=(String)session.getAttribute(Const.CURRENT_USER);
        return reviewService.deleteReview(openId,fileName);
    }
}
