package com.van.book3.controller;

import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.serviceimpl.RankServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Van
 * @date 2020/3/17 - 13:13
 */
@Validated
@RestController
@RequestMapping("/rank")
public class RankController {
    @Resource
    private RankServiceImpl rankService;

    @RequestMapping("/save")
    public ServerResponse save(@NotEmpty String fileName, @NotNull Integer rank, HttpSession session) {
        //need login
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
        return rankService.save(fileName, rank,openId);
    }
}
