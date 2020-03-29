package com.van.book3.utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/3/7 - 14:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class DateUtilTest {
    @Test
    public void test() {
        System.out.println(DateUtil.now());
    }
}