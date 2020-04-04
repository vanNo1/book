package com.van.book3.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.van.book3.common.CodeMsg;
import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.dto.ChangePasswordVO;
import com.van.book3.dto.LoginDTO;
import com.van.book3.entity.User;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.UserService;
import com.van.book3.serviceimpl.DailyAttendanceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;

/**
 * @author Van
 * @date 2020/3/7 - 14:28
 */
@Validated
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private DailyAttendanceServiceImpl dailyAttendanceService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ServerResponse register(@Validated User user) {
        return userService.register(user);
    }

    @RequestMapping("/day")
    public ServerResponse getDay(String openId) {
        return userService.getDay(openId);
    }
    @RequestMapping("/attendance")
    public ServerResponse attendance(HttpSession session){
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
        return dailyAttendanceService.attendance(openId);
    }


    @RequestMapping("/login.do")
    public ServerResponse login(@Validated LoginDTO loginDTO, HttpSession session) {
        String openId=loginDTO.getOpenId();
        String username=loginDTO.getUsername();
        String password=loginDTO.getPassword();
        ServerResponse serverResponse = userService.login(openId, username, password);
        //only success will get here because error is already throw Exception
        session.setAttribute(Const.CURRENT_USER,openId);
        return serverResponse;
    }

    @RequestMapping("/logout.do")
    public ServerResponse logout(@NotEmpty String openId, HttpSession session) {
        String result = (String) session.getAttribute(openId);
        if (result == null) {
            throw new GlobalException(CodeMsg.NOT_LOGIN);
        }
        session.removeAttribute(openId);
        return ServerResponse.success("用户退出成功");
    }

    @RequestMapping("/forget_password.do")
    public ServerResponse forgetPassword(@NotEmpty String openId,@NotEmpty String answer) {
        return userService.forgetPassword(openId, answer);
    }

    @RequestMapping("/forget_reset_password.do")
    public ServerResponse forgetRestPassword(@NotEmpty String openId,@NotEmpty String newPassword) {
        String token = Const.TOKEN_PREFIX + openId;

        return userService.forgetPasswordAndChangePassword(openId, token, newPassword);
    }

    @RequestMapping("/change_password.do")
    public ServerResponse changePassword(@Validated ChangePasswordVO vo) {
        if (vo.getNewPassword().equals(vo.getOldPassword())){
            throw new GlobalException(CodeMsg.SAME_PASSWORD);
        }
        return userService.changePassword(vo.getUsername(), vo.getOldPassword(), vo.getNewPassword(), vo.getOpenId());
    }
}
