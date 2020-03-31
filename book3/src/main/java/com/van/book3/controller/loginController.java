package com.van.book3.controller;

import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.entity.Sign;
import com.van.book3.serviceimpl.SignServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;

/**
 * @author Van
 * @date 2020/3/6 - 12:44
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/openId")
public class loginController {
    @Resource
    SignServiceImpl signService;

    @RequestMapping("/get")
    public ServerResponse login(@NotEmpty String code,@NotEmpty String appId,@NotEmpty String secret, HttpSession session) {
        ServerResponse<Sign> serverResponse = signService.getOpenId(code, appId, secret);
        session.setAttribute(Const.CURRENT_USER, serverResponse.getData());

        return serverResponse;
    }

}
