package com.van.book3.dao;

import com.baomidou.mybatisplus.annotations.TableId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/3/7 - 14:11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class UserMapperTest {
    @Resource
    private UserMapper userMapper;
@Test
    public void test(){
    System.out.println(userMapper.selectById(5));
}
}