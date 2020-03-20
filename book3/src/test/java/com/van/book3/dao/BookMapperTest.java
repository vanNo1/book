package com.van.book3.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.van.book3.entity.Book;
import com.van.book3.entity.HotBook;
import com.van.book3.serviceimpl.HotBookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/3/16 - 12:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BookMapperTest {
    @Resource
    private HotBookServiceImpl hotBookService;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private HotBookMapper hotBookMapper;
@Test
    public void test(){
    Book book=bookMapper.selectById(1);
    System.out.println(book);
}
@Test
    public void insertUnzipPath(){
    Map map=new HashMap();
    List<Book>bookList=bookMapper.selectByMap(map);
    for (Book book : bookList) {
        book.setUnzipPath("/epub2/");
        bookMapper.updateById(book);
    }
}
@Test
    public void qw(){
    QueryWrapper<HotBook>  queryWrapper=new QueryWrapper();
    queryWrapper.select("distinct file_name,count(file_name)as num").groupBy("file_name").orderByDesc("num");
    List<HotBook>hotBookList= hotBookMapper.selectList(queryWrapper);
    System.out.println(hotBookList);

}
@Test
public void test2(){
    hotBookService.pageNum();
}
}