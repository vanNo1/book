package com.van.book3.controller;

import com.van.book3.common.ServerResponse;
import com.van.book3.serviceimpl.BannerServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author Van
 * @date 2020/4/3 - 23:06
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Resource
    private BannerServiceImpl bannerService;
    @RequestMapping("/list")
    public ServerResponse list(){
        return bannerService.generateBanner();
    }
}
