package com.van.book3.serviceimpl;

import com.van.book3.dao.BannerMapper;
import com.van.book3.entity.Banner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/4/3 - 22:52
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BannerServiceImplTest {
    @Resource
    private BannerMapper bannerMapper;
    @Test
    public void test(){
        List<Banner>banners=bannerMapper.selectByMap(null);
        System.out.println(banners);
    }
}