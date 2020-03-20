package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.book3.dao.BookMapper;
import com.van.book3.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/3/18 - 11:17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceImplTest {
@Resource
    private BookServiceImpl bookService;
@Resource
    private BookMapper bookMapper;
@Test
    public void recomment(){
    bookService.recomment();
}
@Test
    public void hotBook(){
    bookService.hotBook();
}
@Test
    public void search(){
    bookService.search("2018",1,20);
}
@Test
    public void map(){
    Map map=new HashMap();
    map.put("file_name",null);
    List<Book>bookList=bookMapper.selectByMap(map);
    System.out.println(bookList);
    //find nothing.null can't search all
}
@Test
    public void selectList(){
    bookService.searchList(null,null,2,"o");
}
@Test
    public void test(){
    QueryWrapper queryWrapper=new QueryWrapper();
}
}