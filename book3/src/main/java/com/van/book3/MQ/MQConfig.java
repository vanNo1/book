//package com.van.book3.MQ;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author Van
// * @date 2020/4/2 - 16:27
// */
//@Slf4j
//@Configuration
//public class MQConfig {
//    public static final String DIRECT_QUEUE="directQueue";
//    @Bean
//    public Queue directQueue(){
//        log.info("create queue:{}",DIRECT_QUEUE);
//        return new Queue(DIRECT_QUEUE);
//    }
//}
