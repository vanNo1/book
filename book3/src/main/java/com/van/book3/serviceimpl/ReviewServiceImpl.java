package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.van.book3.common.CodeMsg;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.ReviewMapper;
import com.van.book3.entity.Book;
import com.van.book3.entity.Rank;
import com.van.book3.entity.Review;
import com.van.book3.entity.User;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.ReviewService;
import com.van.book3.utils.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/4/4 - 11:25
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Resource
    private ReviewMapper reviewMapper;
    @Resource
    private RankServiceImpl rankService;
    @Resource
    private UserServiceImpl userService;
    @Resource
    private BookServiceImpl bookService;
    public ServerResponse insertOrUpdateReview(String openId,String fileName,String summary,String title){
        Integer rankScore =rankService.rank(openId,fileName);
        User user=userService.findUserByOpenId(openId);
        Book book=bookService.selectBookByFileName(fileName);
        //validate the entity
        if (user==null){
            throw new GlobalException(CodeMsg.NOT_EXIST);
        }
        if (book==null){
            throw new GlobalException(CodeMsg.BOOK_NOT_EXIST);
        }
        //if review is exist
        Map map=new HashMap();
        map.put("open_id",openId);
        map.put("file_name",fileName);
        List<Review> reviewList=reviewMapper.selectByMap(map);
        if (reviewList.size()==0){
            //assemble review
            Review review=new Review();
            review.setAvatarUrl(user.getAvatarUrl());
            review.setOpenId(openId);
            review.setFileName(fileName);
            review.setAuthor(book.getAuthor());
            review.setRank(rankScore);
            review.setTitle(title);
            review.setSummary(summary);

            //insert
            reviewMapper.insert(review);
            return ServerResponse.success("评论成功");
        }
        //assemble review
        Review review=reviewList.get(0);
        review.setAvatarUrl(user.getAvatarUrl());
        review.setOpenId(openId);
        review.setFileName(fileName);
        review.setAuthor(book.getAuthor());
        review.setRank(rankScore);
        review.setTitle(title);
        review.setSummary(summary);
        //insert
        reviewMapper.updateById(review);
        return ServerResponse.success("评论成功");

    }
    public ServerResponse<List<Review>> listReview(String fileName,int pageSize,int current){
        Page<Review>page= new Page<>(current,pageSize,false);
        QueryWrapper<Review>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("file_name",fileName);
        IPage iPage =reviewMapper.selectPage(page,queryWrapper);
        List<Review>reviews=iPage.getRecords();

        return ServerResponse.success("获取成功",reviews);
    }
    public ServerResponse deleteReview(String openId,String fileName){
        Map map=new HashMap();
        map.put("open_id",openId);
        map.put("file_name",fileName);
        int count=reviewMapper.deleteByMap(map);
        if (count==0){
            throw new GlobalException(CodeMsg.DB_ERROR);
        }
        return ServerResponse.success("删除成功");
    }

}
