package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.van.book3.common.CodeMsg;
import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.BookMapper;
import com.van.book3.dao.RankMapper;
import com.van.book3.entity.Book;
import com.van.book3.entity.Rank;
import com.van.book3.entity.Sign;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.RankService;
import com.van.book3.utils.LoginUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/17 - 12:10
 */
@Service
public class RankServiceImpl implements RankService {
    @Resource
    private RankMapper rankMapper;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookServiceImpl bookService;

    public ServerResponse save(String fileName, Integer rank, String openId) {
        //if don't have this book
        Book book=bookService.selectBookByFileName(fileName);
        if (book==null){
            throw new GlobalException(CodeMsg.BOOK_NOT_EXIST);
        }
        //if user have ranked before
        Map map=new HashMap();
        map.put("file_name",fileName);
        map.put("open_id",openId);
        List<Rank> rankList=rankMapper.selectByMap(map);
        if (rankList.size()!=0){
            rankMapper.updateById(rankList.get(0));
            return ServerResponse.success("更新评论成功");
        }
        //if user have not rank before
        Rank userRank = new Rank();
        userRank.setFileName(fileName);
        userRank.setOpenId(openId);
        userRank.setRank(rank);
        int count = rankMapper.insert(userRank);
        if (count > 0) {
            return ServerResponse.success("保存评分成功");
        } else {
            throw new GlobalException(CodeMsg.RANK_SAVE_FAIL);
        }

    }

    public Integer rank(String openId,String fileName) {
        if (openId == null) {
            return null;
        }
        Map map = new HashMap();
        map.put("open_id", openId);
        map.put("file_name", fileName);
        List<Rank> rankList = rankMapper.selectByMap(map);
        if (rankList.size() > 0) {
            return rankList.get(0).getRank();
        } else {
            return null;
        }
    }

    public double rankAvg(String fileName) {
        Map map = new HashMap();
        map.put("file_name", fileName);
        List<Rank> rankList = rankMapper.selectByMap(map);
        if (rankList.size() == 0) {
            return 0;
        }
        int sum = 0;
        for (Rank rank : rankList) {
            sum += rank.getRank();
        }
        return sum / rankList.size();
    }

    public int rankNum(String fileName) {
        Map map = new HashMap();
        map.put("file_name", fileName);
        List<Rank> rankList = rankMapper.selectByMap(map);
        if (rankList.size() == 0) {
            return 0;
        } else {
            return rankList.size();
        }
    }

    public List<Book> getHighRankBookByCategory(int category,int rank,int size){
        QueryWrapper<Rank>rankQueryWrapper=new QueryWrapper<>();
        rankQueryWrapper.select("count(*) as nums,file_name,open_id,rank")
                .eq("rank",rank).eq("category",category).groupBy("file_name")
                .orderByDesc("nums");
        Page<Rank>rankPage=new Page<>(1,size,false);
        IPage<Rank> iPage =rankMapper.selectPage(rankPage,rankQueryWrapper);
        List<Rank> rankList=iPage.getRecords();
        List<String> bookNameList = new ArrayList<>();
        for (Rank rankItem : rankList) {
            bookNameList.add(rankItem.getFileName());
        }
        //...........................in bookNameList
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("file_name", bookNameList);
        List<Book> bookList = bookMapper.selectList(queryWrapper);
        return bookList;
    }


    public List<Book> getHighRankBook(int rank) {
        //get all the names

        QueryWrapper<Rank>rankQueryWrapper=new QueryWrapper<>();
        rankQueryWrapper.select("count(*) as nums,file_name,open_id,rank")
                .eq("rank",5).groupBy("file_name")
                .orderByDesc("nums");
        Page<Rank>rankPage=new Page<>(1,3,false);
        IPage<Rank> iPage =rankMapper.selectPage(rankPage,rankQueryWrapper);
        List<Rank> rankList=iPage.getRecords();
        List<String> bookNameList = new ArrayList<>();
        for (Rank rankItem : rankList) {
            bookNameList.add(rankItem.getFileName());
        }
        //...........................in bookNameList
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("file_name", bookNameList);
        List<Book> bookList = bookMapper.selectList(queryWrapper);
        return bookList;

    }

}
