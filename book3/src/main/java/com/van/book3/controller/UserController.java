package com.van.book3.controller;

import com.van.book3.dao.UserMapper;
import com.van.book3.entity.User;
import com.van.book3.service.UserService;
import com.van.book3.utils.RegisterVOUtil;
import com.van.book3.vo.RegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Van
 * @date 2020/3/7 - 14:28
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
@Resource
private UserService userService;

    @RequestMapping("/register")
    public RegisterVO register(User user){
        try{
            userService.insert(user);
            log.info("register success");
            return RegisterVOUtil.success();
        }catch (Exception e){
            log.error("register error");
            return RegisterVOUtil.error();
        }
    }
}
