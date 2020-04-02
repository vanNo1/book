package com.van.book3.interceptor;

import com.van.book3.common.CodeMsg;
import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author Van
 * @date 2020/4/1 - 15:28
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
        String openId=(String) session.getAttribute(Const.CURRENT_USER);
        if (openId==null){
            //要在interceptor里面返回出来json，就得做一下response的配置,要写在不成功的条件里面，不能既手动调用又程序调用，否则会报异常
            response.reset();//重置response 要不然会报错
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");//说明要返回json
            PrintWriter writer=response.getWriter();
            writer.println(JsonUtil.object2String(ServerResponse.error(CodeMsg.NOT_LOGIN)));
            writer.flush();//清空print中的值
            writer.close();//关闭它
            log.info("user not login!");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
