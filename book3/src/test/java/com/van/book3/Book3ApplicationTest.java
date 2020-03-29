package com.van.book3;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/3/7 - 11:53
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class Book3ApplicationTest {
    @Test
    public void date() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String now = simpleDateFormat.format(date);
        long nowl = Long.valueOf(now);
        System.out.println(nowl);

    }
}