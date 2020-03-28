package com.van.mall.dao;

import com.van.mall.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Van
 * @date 2020/3/8 - 20:36
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void test() {
        User user = new User();
        user.setUsername("jack");
        user.setPassword("321");
        userMapper.insert(user);
    }


}