//package com.van.book3.MQ;
//
//import com.van.book3.common.CodeMsg;
//import com.van.book3.common.ServerResponse;
//import com.van.book3.serviceimpl.BookServiceImpl;
//import com.van.book3.utils.JsonUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * @author Van
// * @date 2020/4/2 - 16:29
// */
//@Component
//@Slf4j
//public class Receiver {
//    @Resource
//    private BookServiceImpl bookService;
//    @RabbitListener(queues = MQConfig.DIRECT_QUEUE)
//    public void receive(String mes){
//        if (mes.equals("null")){
//            mes=null;
//        }
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = servletRequestAttributes.getResponse();
//        log.info("receiveMessage:{}",mes);
//        ServerResponse serverResponse=bookService.getHomeData(mes);
//        //要在interceptor里面返回出来json，就得做一下response的配置,要写在不成功的条件里面，不能既手动调用又程序调用，否则会报异常
//        response.reset();//重置response 要不然会报错
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json;charset=UTF-8");//说明要返回json
//        PrintWriter writer= null;
//        try {
//            writer = response.getWriter();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        writer.println(serverResponse);
//        writer.flush();//清空print中的值
//        writer.close();//关闭它
//    }
//}
