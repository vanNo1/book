package com.van.book3.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/4/1 - 16:26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShelfMapperTest {
    @Resource
    private ShelfMapper shelfMapper;
    @Test
    public void test(){
        Map map=new HashMap();
        map.put("file_name","asd");
        map.put("open_id","sad");
        shelfMapper.deleteByMap(map);
    }
}