package com.van.mall.controller.common;

import com.van.mall.common.Const;
import com.van.mall.entity.User;
import com.van.mall.util.CookieUtil;
import com.van.mall.util.JsonUtil;
import com.van.mall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Van
 * @date 2020/3/22 - 16:04
 */
@Component
@WebFilter(urlPatterns = "*.do",filterName = "sessionExpireFilter")
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        String loginToken= CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isNotEmpty(loginToken)){
            String userStr= RedisPoolUtil.get(loginToken);
            User user= JsonUtil.string2Object(userStr,User.class);
            if(user!=null){
                RedisPoolUtil.expire(loginToken, Const.ReidsCacheExTime.REDIS_SESSION_EXTIME);
            }
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
