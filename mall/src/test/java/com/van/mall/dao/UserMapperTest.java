package com.van.mall.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.van.mall.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void test(){
    Map<String,Object> map=new HashMap<>();
    map.put("username","van");
    List<User>userList =userMapper.selectByMap(map);
    userList.forEach(System.out::println);
}
    @Test
    public void test2(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("username",null)
                    .isNotNull("email");
        List<User>userList=userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    @Test
    public void insert(){
    User user=new User();
    user.setRole(0);
    user.setUsername("fuck");
    user.setPassword("321");
    userMapper.insert(user);
    }
    @Test
    public void update(){
        User user=new User();
        user.setId(22);
        user.setRole(0);
        user.setUsername("van");
        user.setPassword("3160");
        userMapper.updateById(user);
    }
    @Test
    public void test3(){
        Integer a = 1;
        Integer b = 1;
        Integer a1 = 128;
        Integer b1 = 128;
        Integer c = null;
        System.out.println("比较第一组:"+(a == b));//true
        System.out.println("比较第二组:"+(a1 == b1));//false
        System.out.println("Integer 与直接数字的比较:" + (a1 == 128));
        System.out.println("使用intValue方法后比较");
        System.out.println(a1.intValue() == b1.intValue());

    }
    @Test
    public void selectById(){
    User user=userMapper.selectById(8);
        System.out.println(user);
    }
    @Test
    public void page(){
        Page<User>page=new Page<>(2,2);
        QueryWrapper  queryWrapper=new QueryWrapper();
        queryWrapper.like("username","v");
        IPage<User> userIPage=userMapper.selectPage(page,queryWrapper);
        System.out.println("Current:"+userIPage.getCurrent());
        System.out.println("Total:"+userIPage.getTotal());
        System.out.println("Pages:"+userIPage.getPages());
        System.out.println("Size:"+userIPage.getSize());
        List<User>users=userIPage.getRecords();
        users.forEach(System.out::println);
    }
}