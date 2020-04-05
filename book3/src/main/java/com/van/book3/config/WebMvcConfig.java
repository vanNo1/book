package com.van.book3.config;

import com.van.book3.interceptor.AuthorityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Van
 * @date 2020/4/1 - 15:31
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns("/shelf/**/**")
        .addPathPatterns("/user/**/**").excludePathPatterns("/user/register","/user/login.do","/user/forget_password.do","/user/forget_reset_password.do")
        .addPathPatterns("/rank/**/**")
        .addPathPatterns("/review/**/**").excludePathPatterns("/review/list")
        .addPathPatterns("/bookList/**/**")
        ;
        super.addInterceptors(registry);
    }
}
