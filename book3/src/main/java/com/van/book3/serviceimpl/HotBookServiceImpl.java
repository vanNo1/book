package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.van.book3.common.ServerResponse;
import com.van.book3.dao.HotBookMapper;
import com.van.book3.entity.Book;
import com.van.book3.entity.HotBook;
import com.van.book3.service.HotBookService;
import com.van.book3.utils.RandomUtil;
import com.van.book3.vo.HotBookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/19 - 10:34
 */
@Service
@Slf4j
public class HotBookServiceImpl implements HotBookService {
    @Resource
    private HotBookMapper hotBookMapper;
    @Resource
    private BookServiceImpl bookService;

    public int insert(String openId, String title, String fileName) {
        HotBook hotBook = new HotBook();
        hotBook.setFileName(fileName);
        hotBook.setOpenId(openId);
        hotBook.setTitle(title);
        return hotBookMapper.insert(hotBook);
    }

    public Long pageNum() {
        Page<HotBook> page = new Page<>(1, 10);
        QueryWrapper<HotBook> queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct file_name as fileName,title,count(file_name)as num")
                .groupBy("file_name")
                .orderByDesc("num");
        IPage iPage = hotBookMapper.selectPage(page, queryWrapper);
        log.info("pageNum: {}", iPage.getPages());
        return iPage.getPages();
    }

    public ServerResponse hotSearch() {
        //get records number
        int pageNumber = pageNum().intValue();
        int randomPage = RandomUtil.getRandomNum(pageNumber, 1);
        //....................................
        Page<HotBook> page = new Page<>(randomPage, 10, false);
        QueryWrapper<HotBook> queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct file_name as fileName,title,count(file_name)as num")
                .groupBy("file_name")
                .orderByDesc("num");
        IPage iPage = hotBookMapper.selectPage(page, queryWrapper);
        List<HotBook> hotBookList = iPage.getRecords();
        List<HotBookVO> hotBookVOList = new ArrayList<>();
        for (HotBook hotBook : hotBookList) {
            HotBookVO hotBookVO = new HotBookVO();
            hotBookVO.setFileName(hotBook.getFileName());
            hotBookVO.setTitle(hotBook.getTitle());
            hotBookVOList.add(hotBookVO);
        }
        return ServerResponse.success("查询成功", hotBookVOList);
    }
    public int readerCount(String fileName){
        Map map=new HashMap();
        map.put("file_name",fileName);
        List<HotBook>hotBookList=hotBookMapper.selectByMap(map);
        if (hotBookList.size()==0){
            return 0;
        }
        return hotBookList.size();
    }
    public List<Book> hotBookThree(String openId){
        QueryWrapper<HotBook>queryWrapper=new QueryWrapper<>();
        queryWrapper.select("count(*) as nums,open_id,file_name,title")
                .eq("open_id",openId)
                .groupBy("file_name")
                .orderByDesc("nums");
        Page<HotBook>page=new Page<>(1,3);
        IPage iPage=hotBookMapper.selectPage(page,queryWrapper);
        List<HotBook>hotBookList=iPage.getRecords();
        //if this user have on history
        if (hotBookList.size()==0){
            return null;
        }
        List<Book>bookList=new ArrayList<>();
        for (HotBook hotBook : hotBookList) {
            Book book=bookService.selectBookByFileName(hotBook.getFileName());
            bookList.add(book);
        }
            return bookList;
    }

}
