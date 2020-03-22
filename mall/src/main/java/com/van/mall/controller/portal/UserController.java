package com.van.mall.controller.portal;

import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.entity.User;
import com.van.mall.service.serviceImpl.UserServiceImpl;
import com.van.mall.util.CookieUtil;
import com.van.mall.util.JsonUtil;
import com.van.mall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Van
 * @date 2020/3/9 - 20:31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserServiceImpl userService;
@RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public ServerResponse login(@RequestParam String username, @RequestParam String password, HttpSession session , HttpServletResponse httpServletResponse){

    ServerResponse response=userService.login(username,password);
    if (response.isSuccess()){
//        session.setAttribute("current_user",Const.CURRENT_USER);
        //sessionId= 3D5D2DBC9DF714996B58FA5297920E36
        CookieUtil.writeLoginToken(httpServletResponse,session.getId());
        RedisPoolUtil.setEx(session.getId(), JsonUtil.object2String(response.getData()),Const.ReidsCacheExTime.REDIS_SESSION_EXTIME);

    }
    return response;
}

    @RequestMapping(value = "/logout.do")
    public ServerResponse logout(HttpServletRequest request,HttpServletResponse response){
//    session.removeAttribute(Const.CURRENT_USER);
        String loginToken=CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(response,request);
        RedisPoolUtil.del(loginToken);
        return ServerResponse.success(null);}

    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public ServerResponse register(User user){
    return userService.register(user);
    }

    @RequestMapping(value = "/check_valid.do")
    public ServerResponse checkValid(String str,String type){
    return  userService.checkValid(str,type);
    }
    @RequestMapping(value = "/get_user_info.do")
    public ServerResponse getUserInfo(HttpSession session){
    User user=(User) session.getAttribute(Const.CURRENT_USER);
    if (user==null){
        return ServerResponse.error("用户未登录");
    }
    return ServerResponse.success(user);
    }

    @RequestMapping(value = "/forget_get_question.do")
    public ServerResponse forgetGetQuestion(String username){
    return userService.selectQuestion(username);
    }

    @RequestMapping(value = "/forget_check_answer.do")
    public ServerResponse forgetCheckAnswer(String username,String question,String answer){
        return userService.returnToken(username,question,answer);
    }

    @RequestMapping(value = "/forget_reset_password.do")
    public ServerResponse forgetResetPassword(@RequestParam String username,@RequestParam String newPassword,@RequestParam String forgetToken){
    return userService.forgetResetPassword(username,newPassword,forgetToken);
    }
    @RequestMapping(value = "/reset_password.do")
    public ServerResponse resetPassword(@RequestParam String passwordNew,@RequestParam String passwordOld,HttpSession session){
    User user=(User)session.getAttribute(Const.CURRENT_USER);
    if (user==null){
        return ServerResponse.error("用户未登录");
    }
    return userService.resetPassword(passwordNew,passwordOld,user);
    }

    @RequestMapping(value = "/update_information.do")
    public ServerResponse updateInformation(HttpSession session,User user){
        User currentUser=(User)session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.error("用户未登录");
        }
        user.setId(currentUser.getId());//user will not post id  so i have to add by myself
        int count=userService.updateUserInfomation(user);
        if (count>0){
            session.setAttribute(Const.CURRENT_USER,user);
            return ServerResponse.success(user);
        }
        return ServerResponse.error("修改用户信息失败");
    }

    @RequestMapping(value = "/get_information.do")
    public ServerResponse getInformation(HttpSession session,HttpServletRequest request){
//        User currentUser=(User)session.getAttribute(Const.CURRENT_USER);
        String loginToken=CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.error("用户未登录");
        }
        String userStr=RedisPoolUtil.get(loginToken);
        User currentUser=JsonUtil.string2Object(userStr,User.class);
        if (currentUser==null){
            return ServerResponse.error("用户未登录");
        }
        return userService.getInformatino(currentUser.getId());
    }
}


