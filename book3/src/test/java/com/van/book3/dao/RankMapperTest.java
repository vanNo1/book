package com.van.book3.dao;

import com.van.book3.entity.Book;
import com.van.book3.entity.Rank;
import com.van.book3.serviceimpl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/4/5 - 13:09
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RankMapperTest {
    @Resource
    private RankMapper rankMapper;
    @Resource
    private BookServiceImpl bookService;
    @Resource
    private BookMapper bookMapper;
    @Test
    public void addCategory(){
        List<Rank> rankList=rankMapper.selectByMap(null);
        for (Rank rank : rankList) {
            Book book=bookService.selectBookByFileName(rank.getFileName());
            if (book==null){
                rank.setCategory(99);
                rankMapper.updateById(rank);
                continue;
            }
            rank.setCategory(book.getCategory());
            rankMapper.updateById(rank);
        }
    }
}