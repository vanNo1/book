package com.van.book3.utils;

import com.baomidou.mybatisplus.extension.api.R;
import com.van.book3.common.Const;
import com.van.book3.entity.*;
import com.van.book3.serviceimpl.*;
import com.van.book3.vo.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Van
 * @date 2020/3/17 - 16:14
 */
@Component
public class AssembleVOUtil {
    @Resource
    private UserServiceImpl userService;
    @Resource
    private RankServiceImpl rankService;
    @Resource
    private ShelfServiceImpl shelfService;
    @Resource
    private HotBookServiceImpl hotBookService;
    @Resource
    private IntroductionServiceImpl introductionService;
    @Resource
    private ReviewServiceImpl reviewService;

    public BookListVO assembleBookListVO(List<Book> books,BookList bookList){
        BookListVO bookListVO=new BookListVO();
        bookListVO.setOpenId(bookList.getOpenId());
        bookListVO.setBookList(bookList.getBookList());
        bookListVO.setTitle(bookList.getTitle());
        bookListVO.setLikes(bookList.getLikes());
        List<BookSimplyVO>bookSimplyVOList=assembleBookSimplyVOList(books);
        bookListVO.setBooks(bookSimplyVOList);
        return bookListVO;
    }

    public BookVO assembleBookVO(Book book, String openId) {
        if (book == null) {
            return null;
        }
        BookVO bookVO = new BookVO();
        bookVO.setAuthor(book.getAuthor());
        bookVO.setCategory(book.getCategory());
        bookVO.setCategoryText(book.getCategoryText());
        bookVO.setCover(Const.DOMAIN_IMG+book.getCover());
        bookVO.setFileName(book.getFileName());
        bookVO.setId(book.getId());
        bookVO.setLanguage(book.getLanguage());
        bookVO.setPublisher(book.getPublisher());
        bookVO.setRootFile(book.getRootFile());
        bookVO.setOpf(Const.DOMAIN + book.getUnzipPath() + book.getFileName() + "/" + book.getRootFile());
        bookVO.setTitle(book.getTitle());
        bookVO.setRank(rankService.rank(openId,book.getFileName()));//current user's rank
        bookVO.setRankAvg(rankService.rankAvg(book.getFileName()));
        bookVO.setRankNum(rankService.rankNum(book.getFileName()));
        bookVO.setReaderNum(shelfService.findPeopleNum(book.getFileName()));
        //assemble readerVO




        List<Shelf> shelfList = shelfService.getAllShelf(book.getFileName());
        if (shelfList == null) {
            bookVO.setReaders(null);
        } else {
            //if shelfList is not null
            List<ReaderVO> readers = null;
            //assemble readerVO and add to ReaderVOList
            for (Shelf shelf : shelfList) {
                ReaderVO readerVO = new ReaderVO();
                User user = userService.selectUserByOpenId(shelf.getOpenId());
                readerVO.setAvatarUrl(user.getAvatarUrl());
                readerVO.setCreate_dt(shelf.getCreateTime());
                readerVO.setNickName(user.getNickName());
                readers.add(readerVO);
            }
            bookVO.setReaders(readers);
        }
        return bookVO;
    }

    public static BookSimplyVO assembleBookSimplyVO(Book book) {
        if (book == null) {
            return null;
        }
        BookSimplyVO bookSimplyVO = new BookSimplyVO();
        bookSimplyVO.setAuthor(book.getAuthor());
        bookSimplyVO.setBookId(book.getFileName());
        bookSimplyVO.setCategory(book.getCategory());
        bookSimplyVO.setCategoryText(book.getCategoryText());
        bookSimplyVO.setCover(Const.DOMAIN_IMG+book.getCover());
        bookSimplyVO.setFileName(book.getFileName());
        bookSimplyVO.setId(book.getId());
        bookSimplyVO.setLanguage(book.getLanguage());
        bookSimplyVO.setPublisher(book.getPublisher());
        bookSimplyVO.setRootFile(book.getRootFile());
        bookSimplyVO.setTitle(book.getTitle());
        return bookSimplyVO;
    }
    public List<BookSimplyVO>assembleBookSimplyVOList(List<Book>books){
        List<BookSimplyVO> bookSimplyVOList=new ArrayList<>();
        for (Book book : books) {
            bookSimplyVOList.add(AssembleVOUtil.assembleBookSimplyVO(book));
        }
        return bookSimplyVOList;
    }

    public BookVO2 assembleBookVO2(Book book){
        BookVO2 bookVO2=new BookVO2();

        //hotBook:how many people have have read this book before(hot_book)
        int readerCount=hotBookService.readerCount(book.getFileName());
        bookVO2.setReaderNum(readerCount);
        //rank
        bookVO2.setRankNum(rankService.rankNum(book.getFileName()));
        bookVO2.setRankAvg(rankService.rankAvg(book.getFileName()));
        //introduction
        Introduction introduction= introductionService.getIntroduction(book.getFileName()).getData();
        bookVO2.setContent(introduction.getContent());
        bookVO2.setAuthorIntroduction(introduction.getAuthor());
        //review
        List<Review>reviews=reviewService.listReview(book.getFileName(),6,1).getData();
        bookVO2.setReviewVOS(assembleReviewVO(reviews));
        //book
        bookVO2.setId(book.getId());
        bookVO2.setFileName(book.getFileName());
        bookVO2.setCover(Const.DOMAIN_IMG+book.getCover());
        bookVO2.setTitle(book.getTitle());
        bookVO2.setAuthor(book.getAuthor());
        bookVO2.setPublisher(book.getPublisher());
        bookVO2.setBookId(book.getFileName());
        bookVO2.setCategory(book.getCategory());
        bookVO2.setCategoryText(book.getCategoryText());
        bookVO2.setLanguage(book.getLanguage());
        bookVO2.setRootFile(book.getRootFile());
        return bookVO2;

    }
    public List<ReviewVO> assembleReviewVO(List<Review> reviews){
        List<ReviewVO> reviewVOS=new ArrayList<>();
        for (Review review : reviews) {
            ReviewVO reviewVO=new ReviewVO();
            reviewVO.setAvatarUrl(review.getAvatarUrl());
            reviewVO.setFileName(review.getFileName());
            reviewVO.setRank(review.getRank());
            reviewVO.setTitle(review.getTitle());
            reviewVO.setSummary(review.getSummary());
            reviewVO.setUpdateTime(review.getUpdateTime());
            reviewVOS.add(reviewVO);
        }
        return reviewVOS;
    }

}
