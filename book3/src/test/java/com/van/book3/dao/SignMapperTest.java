package com.van.book3.dao;

import com.van.book3.entity.Sign;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/3/7 - 12:23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SignMapperTest {
    @Resource
    private SignMapper signMapper;

    @Test
    public void test() {
        Sign sign = signMapper.selectById(1);
        System.out.println(sign);
    }
}