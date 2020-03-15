package com.van.book3.controller;

import com.van.book3.common.ServerResponse;
import com.van.book3.serviceimpl.SignServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Van
 * @date 2020/3/6 - 12:44
 */
@Slf4j
@RestController
@RequestMapping("/openId")
public class loginController {
    @Resource
    SignServiceImpl signService;
    @RequestMapping("/get")
    public ServerResponse login(String code, String appId, String secret){
        if (code==null||appId==null||secret==null){
            return ServerResponse.error("参数为空");
        }
        return signService.getOpenId(code,appId,secret);

    }

}
