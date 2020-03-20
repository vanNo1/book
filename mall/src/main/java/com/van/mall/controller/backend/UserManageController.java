package com.van.mall.controller.backend;

import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.entity.User;
import com.van.mall.service.serviceImpl.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Van
 * @date 2020/3/10 - 16:56
 */
@RestController
@RequestMapping("/manage/user")
public class UserManageController {
    @Resource
    private UserServiceImpl userService;
    @RequestMapping(value = "/login.do" ,method = RequestMethod.POST)
    public ServerResponse login(@RequestParam String username,@RequestParam String password, HttpSession session){
        ServerResponse response=userService.login(username,password);
        User user=(User) response.getData();
        if (response.isSuccess()){
            if (user.getRole()==Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,response.getData());
                return response;
            }else {
                return ServerResponse.error("你不是管理员");
            }

        }
        return response;
    }
}
