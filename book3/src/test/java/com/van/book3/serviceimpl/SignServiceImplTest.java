package com.van.book3.serviceimpl;

import com.sun.jnlp.SingleInstanceServiceImpl;
import com.van.book3.entity.Sign;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/3/7 - 14:38
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SignServiceImplTest {
    @Resource
    private SignServiceImpl signService;
@Test
    public void test() {


}
}