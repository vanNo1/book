package com.van.book3.controller;

import com.van.book3.common.ServerResponse;
import com.van.book3.entity.User;
import com.van.book3.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/7 - 14:28
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {
@Resource
private UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ServerResponse register(User user){
    return userService.register(user);
    }
    @RequestMapping("/day")
    public ServerResponse getDay(String openId){
    return userService.getDay(openId);
    }

}
