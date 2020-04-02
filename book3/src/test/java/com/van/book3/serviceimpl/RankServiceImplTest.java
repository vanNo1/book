package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.van.book3.dao.RankMapper;
import com.van.book3.entity.Rank;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Van
 * @date 2020/4/1 - 21:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RankServiceImplTest {
    @Resource
    private RankMapper rankMapper;
    @Test
    public void test(){
        QueryWrapper<Rank>rankQueryWrapper=new QueryWrapper<>();
        rankQueryWrapper.select("count(*) as nums,file_name,open_id,rank").eq("rank",5).groupBy("file_name").orderByDesc("nums");
        Page<Rank> rankPage=new Page<>(1,3,false);
        IPage<Rank> iPage =rankMapper.selectPage(rankPage,rankQueryWrapper);
        List<Rank> rankList=iPage.getRecords();
        System.out.println(rankList.get(0));
    }

}