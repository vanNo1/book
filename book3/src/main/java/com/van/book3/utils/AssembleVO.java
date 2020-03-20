package com.van.book3.utils;

import com.baomidou.mybatisplus.extension.api.R;
import com.van.book3.common.Const;
import com.van.book3.entity.Book;
import com.van.book3.entity.Rank;
import com.van.book3.entity.Shelf;
import com.van.book3.entity.User;
import com.van.book3.serviceimpl.RankServiceImpl;
import com.van.book3.serviceimpl.ShelfServiceImpl;
import com.van.book3.serviceimpl.UserServiceImpl;
import com.van.book3.vo.BookSimplyVO;
import com.van.book3.vo.BookVO;
import com.van.book3.vo.ReaderVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Reader;
import java.util.List;

/**
 * @author Van
 * @date 2020/3/17 - 16:14
 */
@Component
public class AssembleVO {
    @Resource
    private UserServiceImpl userService;
    @Resource
    private RankServiceImpl rankService;
    @Resource
    private ShelfServiceImpl shelfService;
    public  BookVO assembleBookVO(Book book,String openId){
        if (book==null){
            return null;
        }
        BookVO bookVO=new BookVO();
        bookVO.setAuthor(book.getAuthor());
        bookVO.setCategory(book.getCategory());
        bookVO.setCategoryText(book.getCategoryText());
        bookVO.setCover(book.getCover());
        bookVO.setFileName(book.getFileName());
        bookVO.setId(book.getId());
        bookVO.setLanguage(book.getLanguage());
        bookVO.setPublisher(book.getPublisher());
        bookVO.setRootFile(book.getRootFile());
        bookVO.setOpf(Const.DOMAIN+book.getUnzipPath()+book.getFileName()+"/"+book.getRootFile());
        bookVO.setTitle(book.getTitle());
        bookVO.setRank(rankService.rank(openId));
        bookVO.setRankAvg(rankService.rankAvg(book.getFileName()));
        bookVO.setRankNum(rankService.rankNum(book.getFileName()));
        bookVO.setReaderNum(shelfService.findPeopleNum(book.getFileName()));

        List<Shelf>shelfList=shelfService.getAllShelf(book.getFileName());
        if (shelfList==null){
            bookVO.setReaders(null);
        }else {
            //if shelfList is not null
            List<ReaderVO>readers=null;
            //assemble readerVO and add to ReaderVOList
            for (Shelf shelf : shelfList) {
                ReaderVO readerVO=new ReaderVO();
                User user=userService.selectUserByOpenId(shelf.getOpenId());
                readerVO.setAvatarUrl(user.getAvatarUrl());
                readerVO.setCreate_dt(shelf.getCreateTime());
                readerVO.setNickName(user.getNickName());
                readers.add(readerVO);
            }
            bookVO.setReaders(readers);
        }
       return bookVO;
    }
    public static BookSimplyVO assembleBookSimplyVO(Book book){
        if (book==null){
            return null;
        }
        BookSimplyVO bookSimplyVO=new BookSimplyVO();
        bookSimplyVO.setAuthor(book.getAuthor());
        bookSimplyVO.setBookId(book.getFileName());
        bookSimplyVO.setCategory(book.getCategory());
        bookSimplyVO.setCategoryText(book.getCategoryText());
        bookSimplyVO.setCover(book.getCover());
        bookSimplyVO.setFileName(book.getFileName());
        bookSimplyVO.setId(book.getId());
        bookSimplyVO.setLanguage(book.getLanguage());
        bookSimplyVO.setPublisher(book.getPublisher());
        bookSimplyVO.setRootFile(book.getRootFile());
        bookSimplyVO.setTitle(book.getTitle());
        return bookSimplyVO;
    }

}
