//package com.van.mall.configuration;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.filter.DelegatingFilterProxy;
//
///**
// * @author Van
// * @date 2020/3/23 - 11:57
// */
//public class FilterConfiguration {
//    @Bean
//    public FilterRegistrationBean filterDemo4Registration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        //注入过滤器
//        registration.setFilter(new DelegatingFilterProxy());
//        //拦截规则
//        registration.addUrlPatterns("*.do");
//        //过滤器名称
//        registration.setName("springSessionRepositoryFilter");
//        //是否自动注册 false 取消Filter的自动注册
////        registration.setEnabled(false);
//        //过滤器顺序
//        registration.setOrder(1);
//        return registration;
//    }
//}
