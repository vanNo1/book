package com.van.book3.serviceimpl;

import com.van.book3.common.CodeMsg;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.BookListLikeMapper;
import com.van.book3.entity.BookList;
import com.van.book3.entity.BookListLike;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.BookListLikeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/4/5 - 14:42
 */
@Service
public class BookListLikeServiceImpl implements BookListLikeService {
    @Resource
    private BookListLikeMapper bookListLikeMapper;
    @Resource
    private BookListServiceImpl bookListService;
    public ServerResponse likeOrDislike(String openId,String bookList){
        //if bookList is not exist
        BookList bookList1= bookListService.selectBookListByName(bookList);
        if (bookList1==null){
            throw new GlobalException(CodeMsg.BOOK_LIST_NOT_EXIST);
        }
        //select record
        Map map=new HashMap();
        map.put("open_id",openId);
        map.put("book_list",bookList);
       List<BookListLike>bookListLikes=bookListLikeMapper.selectByMap(map);
        //if he like before,then cancel his like
       if (bookListLikes.size()==1){
           BookListLike bookListLike=bookListLikes.get(0);
           bookListLikeMapper.deleteById(bookListLike.getId());
           bookListService.cancelLike(bookList);
           return ServerResponse.success("取消点赞");
       }
       //if he first like then add like =1
        BookListLike bookListLike=new BookListLike();
        bookListLike.setOpenId(openId);
        bookListLike.setBookList(bookList);
        bookListLike.setLiked(1);
        bookListLikeMapper.insert(bookListLike);
        bookListService.addLike(bookList);
        return ServerResponse.success("点赞成功");
    }
}
