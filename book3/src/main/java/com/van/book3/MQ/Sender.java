//package com.van.book3.MQ;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @author Van
// * @date 2020/4/2 - 16:29
// */
//@Component
//@Slf4j
//public class Sender {
//    @Resource
//    private RabbitTemplate rabbitTemplate;
//    public void directSend(String message){
//        if (message==null){
//            message="null";
//        }
//        log.info("sendMessage:{}",message);
//        rabbitTemplate.convertAndSend(MQConfig.DIRECT_QUEUE,message);
//    }
//}
