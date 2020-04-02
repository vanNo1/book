package com.van.book3.utils;

import com.van.book3.common.Const;
import com.van.book3.entity.Sign;

import javax.servlet.http.HttpSession;

/**
 * @author Van
 * @date 2020/3/17 - 12:13
 */
public class LoginUtil {
    public static boolean isLogin(HttpSession session) {
        String  openId = (String) session.getAttribute(Const.CURRENT_USER);
        if (openId == null) {
            return false;
        } else {
            return true;
        }

    }

    public static String getOpenId(HttpSession session) {
        String openId = (String) session.getAttribute(Const.CURRENT_USER);
        if (openId == null) {
            return null;
        } else {
            return openId;
        }
    }

}
