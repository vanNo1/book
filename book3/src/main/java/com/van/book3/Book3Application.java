package com.van.book3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.van.book3.dao")
public class Book3Application {

    public static void main(String[] args) {
        SpringApplication.run(Book3Application.class, args);
    }

}
