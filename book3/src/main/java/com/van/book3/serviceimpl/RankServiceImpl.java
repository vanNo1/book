package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.BookMapper;
import com.van.book3.dao.RankMapper;
import com.van.book3.entity.Book;
import com.van.book3.entity.Rank;
import com.van.book3.entity.Sign;
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
    public ServerResponse save(String fileName, Integer rank, HttpSession session){
        if (!LoginUtil.isLogin(session)){
            return ServerResponse.error("用户未登录");
        }
        String openId=LoginUtil.getOpenId(session);
        Rank userRank=new Rank();
        userRank.setFileName(fileName);
        userRank.setOpenId(openId);
        userRank.setRank(rank);
        int count=rankMapper.insert(userRank);
        if (count>1){
            return ServerResponse.success("保存评分成功",null);
        }else {
            return ServerResponse.error("保存评分失败");
        }

    }
    public Integer rank(String openId){
        Map map=new HashMap();
        map.put("open_id",openId);
        List<Rank>rankList=rankMapper.selectByMap(map);
        if (rankList.size()>0){
            return rankList.get(0).getRank();
        }else {
            return null;
        }
    }
    public double rankAvg(String fileName){
        Map map=new HashMap();
        map.put("file_name",fileName);
        List<Rank>rankList=rankMapper.selectByMap(map);
        if (rankList.size()==0){
            return 0;
        }
        int sum=0;
        for (Rank rank : rankList) {
            sum+=rank.getRank();
        }
        return sum/rankList.size();
    }
    public int rankNum(String fileName){
        Map map=new HashMap();
        map.put("file_name",fileName);
        List<Rank>rankList=rankMapper.selectByMap(map);
        if (rankList.size()==0){
            return 0;
        }else {
            return rankList.size();
        }
    }
    public List<Book> getBookFromRank(int rank){
        //get all the names
        Map map=new HashMap();
        map.put("rank",rank);
        List<String>bookNameList=new ArrayList<>();
        List<Rank>rankList=rankMapper.selectByMap(map);
        for (Rank rankItem : rankList) {
            bookNameList.add(rankItem.getFileName());
        }
        //...........................in bookNameList
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in("file_name",bookNameList);
        List<Book>bookList=bookMapper.selectList(queryWrapper);
        return bookList;

    }

}
