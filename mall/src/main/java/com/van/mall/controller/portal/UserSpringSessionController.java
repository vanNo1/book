package com.van.mall.controller.portal;

import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.entity.User;
import com.van.mall.service.serviceImpl.UserServiceImpl;
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
 * @date 2020/3/23 - 11:26
 */
@RestController
@RequestMapping("/user/springSession")
public class UserSpringSessionController {
    @Resource
    private UserServiceImpl userService;
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public ServerResponse login(@RequestParam String username, @RequestParam String password, HttpSession session , HttpServletResponse httpServletResponse){

        ServerResponse response=userService.login(username,password);
        if (response.isSuccess()){
        session.setAttribute(Const.CURRENT_USER,response.getData());
            //sessionId= 3D5D2DBC9DF714996B58FA5297920E36
//            CookieUtil.writeLoginToken(httpServletResponse,session.getId());
//            RedisPoolUtil.setEx(session.getId(), JsonUtil.object2String(response.getData()), Const.ReidsCacheExTime.REDIS_SESSION_EXTIME);

        }
        return response;
    }

    @RequestMapping(value = "/logout.do")
    public ServerResponse logout(HttpServletRequest request, HttpServletResponse response, HttpSession session ){
    session.removeAttribute(Const.CURRENT_USER);
//        String loginToken=CookieUtil.readLoginToken(request);
//        CookieUtil.delLoginToken(response,request);
//        RedisPoolUtil.del(loginToken);
        return ServerResponse.success(null);}
    @RequestMapping(value = "/get_user_info.do")
    public ServerResponse getUserInfo(HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("用户未登录");
        }
        return ServerResponse.success(user);
    }
}
