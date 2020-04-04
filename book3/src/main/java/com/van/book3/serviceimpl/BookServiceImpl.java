package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.van.book3.common.CodeMsg;
import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.BookMapper;
import com.van.book3.entity.Book;
import com.van.book3.entity.History;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.BookService;
import com.van.book3.utils.AssembleVOUtil;
import com.van.book3.utils.RandomUtil;
import com.van.book3.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Van
 * @date 2020/3/16 - 12:02
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Resource
    private CategoryServiceImpl categoryService;
    @Resource
    private ShelfServiceImpl shelfService;
    @Resource
    private AssembleVOUtil assembleVOUtil;
    @Resource
    private RankServiceImpl rankService;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private HotSearchServiceImpl hotSearchService;
    @Resource
    private HistoryServiceImpl historyService;

    public ServerResponse selectBookDetailV2(String fileName){
        Map map=new HashMap();
        map.put("file_name",fileName);
        List<Book>bookList=bookMapper.selectByMap(map);
        if (bookList.size()==0){
            throw new GlobalException(CodeMsg.BOOK_NOT_EXIST);
        }
        BookVO2 bookVO2=assembleVOUtil.assembleBookVO2(bookList.get(0));
        return ServerResponse.success("获取成功",bookVO2);
    }
    public Book selectBookByFileName(String fileName) {
        Map map = new HashMap();
        map.put("file_name", fileName);
        List<Book> bookList = bookMapper.selectByMap(map);
        if (bookList.size() > 0) {
            return bookList.get(0);
        } else {
            return null;
        }
    }

    public ServerResponse getDetail(String openId, String fileName) {
        Book book = selectBookByFileName(fileName);
        if (openId != null) {
            History history = new History();
            history.setOpenId(openId);
            history.setFileName(fileName);
            historyService.insert(history);
        }
        BookVO bookVO = assembleVOUtil.assembleBookVO(book, openId);
        if (bookVO == null) {
            return ServerResponse.error("获取失败,查无此书");
        } else {
            return ServerResponse.success("获取成功", bookVO);
        }
    }

    //get random books
    public ServerResponse<List<BookSimplyVO>> recomment() {
        Set<Integer> books = RandomUtil.getRandomSet(Const.MAXBOOKS, 1, 3);
        List<BookSimplyVO> bookList = new ArrayList<>();
        for (Integer bookId : books) {
            Book book = bookMapper.selectById(bookId);
            bookList.add(AssembleVOUtil.assembleBookSimplyVO(book));
        }
        return ServerResponse.success("查询成功", bookList);

    }

    //select three books which rank is 5 score
    public ServerResponse<List<BookSimplyVO>> hotBook() {
        //get books which score is 5
        List<Book> bookList = rankService.getHighRankBook(5);
        //................................
        List<BookSimplyVO> bookSimplyVOList = new ArrayList<>();
        //bookList have only three books
        for (Book book : bookList) {
            BookSimplyVO bookSimplyVO = AssembleVOUtil.assembleBookSimplyVO(book);
            bookSimplyVOList.add(bookSimplyVO);
        }
        return ServerResponse.success("查询成功", bookSimplyVOList);
    }

    //select page
    public ServerResponse search(String keyword, int page, int pageSize) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("file_name", keyword);
        Page<Book> bookPage = new Page<Book>(page, pageSize, false);
        IPage iPage = bookMapper.selectPage(bookPage, queryWrapper);
        List<Book> bookList = iPage.getRecords();
        //if book is not exist
        if (bookList.size()==0){
            throw new GlobalException(CodeMsg.BOOK_NOT_EXIST);
        }
        List<BookSimplyVO> bookSimplyVOList = new ArrayList<>();
        for (Book book : bookList) {
            BookSimplyVO bookSimplyVO = AssembleVOUtil.assembleBookSimplyVO(book);
            bookSimplyVOList.add(bookSimplyVO);
        }
        return ServerResponse.success("查询成功", bookSimplyVOList);
    }

    //select by publisher category categoryId author
    public ServerResponse searchList(String publisher, String category, Integer categoryId, String author) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper();
        if (publisher != null) {
            queryWrapper.like("publisher", publisher);
        }
        if (category != null) {
            queryWrapper.like("category_text", category);
        }
        if (categoryId != null) {
            queryWrapper.eq("category", categoryId);
        }
        if (author != null) {
            queryWrapper.like("author", author);
        }
        List<Book> bookList = bookMapper.selectList(queryWrapper);
        List<BookSimplyVO> bookSimplyVOList = new ArrayList<>();
        for (Book book : bookList) {
            BookSimplyVO bookSimplyVO = AssembleVOUtil.assembleBookSimplyVO(book);
            bookSimplyVOList.add(bookSimplyVO);
        }
        return ServerResponse.success("查询成功", bookSimplyVOList);
    }

    public ServerResponse getHomeData(String openId) {
        HomeVO homeVO=new HomeVO();
        //shelf
        List<ShelfVO> shelfVOList = new ArrayList<>();
        if (openId == null) {
            shelfVOList = null;
        } else {
            try {
                shelfVOList = shelfService.get(null,openId).getData();
            } catch (Exception e) {
                shelfVOList=null;
            }
        }
        homeVO.setShelf(shelfVOList);
        //category
        List<CategoryVO> categoryVOList = categoryService.list().getData();
        homeVO.setCategory(categoryVOList);
        //recommend
        List<BookSimplyVO> bookList = recomment().getData();
        homeVO.setRecommend(bookList);
        //hotbook
        List<BookSimplyVO> hotBookList = hotBook().getData();
        homeVO.setHotBook(hotBookList);
        //freeread
        List<BookSimplyVO> freeRead = recomment().getData();
        homeVO.setFreeRead(freeRead);
        //shelfCount
        int shelfCount;
        if (openId == null) {
            shelfCount = 0;
        } else {
            shelfCount = shelfService.getShelfNumber(openId);
        }
        homeVO.setShelfCount(shelfCount);
        //HotSearchVO
        //todo hotsearch
        HotSearchVO hotSearchVO = hotSearchService.getHotSearchVO();
        homeVO.setHotSearch(hotSearchVO);
        //bannerVO
        BannerVO bannerVO = new BannerVO();
        bannerVO.setImg("https://www.youbaobao.xyz/book/res/bg.jpg");
        bannerVO.setSubTitle("马上学习");
        bannerVO.setTitle("mpvue2.0多端小程序课程重磅上线");
        bannerVO.setUrl("https://www.youbaobao.xyz/book/#/book-store/shelf");
        homeVO.setBanenr(bannerVO);

        return ServerResponse.success("查询成功", homeVO);
    }
}