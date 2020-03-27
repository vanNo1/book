package com.van.book3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan("com.van.book3.dao")
@EnableRedisHttpSession
public class Book3Application {

    public static void main(String[] args) {
        SpringApplication.run(Book3Application.class, args);
    }

}
