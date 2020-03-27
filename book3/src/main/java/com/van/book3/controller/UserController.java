package com.van.book3.controller;

import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.entity.User;
import com.van.book3.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    @RequestMapping("/login.do")
    public ServerResponse login(String openId, String username, String password, HttpSession session){
        ServerResponse serverResponse=userService.login(openId,username,password);
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }
        session.setAttribute(openId, Const.CURRENT_USER);
        return ServerResponse.success("用户登录成功");
    }
    @RequestMapping("/logout.do")
    public ServerResponse logout(String openId,HttpSession session){
        String result=(String) session.getAttribute(openId);
        if (result==null){
            return ServerResponse.error("用户未登录退出失败");
        }
        session.removeAttribute(openId);
        return ServerResponse.success("用户退出成功");
    }
    @RequestMapping("/forget_password.do")
    public ServerResponse forgetPassword(String openId,String answer){
        return userService.forgetPassword(openId,answer);
    }
    @RequestMapping("/forget_reset_password.do")
    public ServerResponse forgetRestPassword(String openId,String newPassword){
        String token="token_"+openId;

        return userService.forgetPasswordAndChangePassword(openId,token,newPassword);
    }

    @RequestMapping("/change_password.do")
    public ServerResponse changePassword(String username,String oldPassword,String newPassword,String openId){
        //todo if user is login
        return userService.changePassword(username,oldPassword,newPassword,openId);
    }
}
