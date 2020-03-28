package com.van.mall.controller.portal;

import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.entity.User;
import com.van.mall.service.serviceImpl.CartServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Van
 * @date 2020/3/12 - 11:44
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartServiceImpl cartService;

    @RequestMapping("/add.do")
    public ServerResponse add(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.error("需要登录");
        }
        return cartService.add(user.getId(), productId, count);
    }

    @RequestMapping("/update.do")
    public ServerResponse update(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.error("需要登录");
        }
        return cartService.update(user.getId(), productId, count);
    }

    @RequestMapping("/delete.do")
    public ServerResponse delete(HttpSession session, String productIds) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.error("需要登录");
        }
        return cartService.delete(user.getId(), productIds);
    }
}

