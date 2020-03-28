package com.van.mall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Van
 * @date 2020/3/22 - 9:53
 */
@Slf4j
public class CookieUtil {
    private final static String COOKID_DOMIAN = ".happymmal.com";
    private final static String COOKID_NAME = "mmall_login_token";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("read cookie name={},value{}", cookie.getName(), cookie.getValue());
                if (StringUtils.equals(COOKID_NAME, cookie.getName())) {
                    log.info("return cookie name={},value={}", cookie.getName(), cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;

    }

    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKID_NAME, token);
//        cookie.setDomain(COOKID_DOMIAN);
        cookie.setPath("/");//root path
        //unit is second
        //if don't set it will only in member not in disk
        cookie.setMaxAge(60 * 60 * 24 * 365);//-1 is permanent
        log.info("write cookie name{} cookie value{}", cookie.getName(), cookie.getValue());
        response.addCookie(cookie);


    }

    public static void delLoginToken(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), COOKID_NAME)) {
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
//                    cookie.setDomain(COOKID_DOMIAN);
                    response.addCookie(cookie);
                    log.info("del cookie name={},value={}", cookie.getName(), cookie.getValue());
                    return;
                }
            }
        }

    }
}
