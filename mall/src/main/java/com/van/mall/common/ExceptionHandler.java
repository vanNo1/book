//    package com.van.mall.common;
//
//    import lombok.extern.slf4j.Slf4j;
//    import org.springframework.web.bind.annotation.ControllerAdvice;
//    import org.springframework.web.bind.annotation.ResponseBody;
//
//    /**
//     * @author Van
//     * @date 2020/3/24 - 10:49
//     */
//    @Slf4j
//    @ControllerAdvice
//    public class ExceptionHandler {
//        @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
//        @ResponseBody
//        public ServerResponse handler(Exception e){
//          log.error("system error",e);
//            return    ServerResponse.error("zero can divide");
//        }
//    }
