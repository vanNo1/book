package com.van.book3.controller;

import com.van.book3.common.ServerResponse;
import com.van.book3.serviceimpl.RankServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Van
 * @date 2020/3/17 - 13:13
 */
@RestController
@RequestMapping("/rank")
public class RankController {
    @Resource
    private RankServiceImpl rankService;

    @RequestMapping("/save")
    public ServerResponse save(String fileName, int rank, HttpSession session) {
        return rankService.save(fileName, rank, session);
    }
}
