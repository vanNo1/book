package com.van.book3.serviceimpl;

import com.van.book3.common.CodeMsg;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.BookListMapper;
import com.van.book3.entity.BookList;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.BookListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/4/5 - 14:27
 */
@Service
public class BookListServiceImpl implements BookListService {
    @Resource
    private BookListMapper bookListMapper;
    public ServerResponse deleteBookListByName(String openId,String bookList){
        Map map=new HashMap();
        map.put("book_list",bookList);
        map.put("open_id",openId);
        int count= bookListMapper.deleteByMap(map);
        if (count==0){
            return ServerResponse.error("删除书单不成功");
        }
        return ServerResponse.success("删除书单成功");
    }
    public BookList selectBookListByName(String bookList){
        Map map=new HashMap();
        map.put("book_list",bookList);
        List<BookList> bookListEntitys=bookListMapper.selectByMap(map);
        if (bookListEntitys.size()==0){
            return null;
        }
        return bookListEntitys.get(0);
    }
    public void addLike(String  bookList){
        //default believe book_list is exist
        BookList bookList1=selectBookListByName(bookList);
        bookList1.setLikes(bookList1.getLikes()+1);
        bookListMapper.updateById(bookList1);
    }
    public void cancelLike(String  bookList){
        //default believe book_list is exist
        BookList bookList1=selectBookListByName(bookList);
        bookList1.setLikes(bookList1.getLikes()-1);
        bookListMapper.updateById(bookList1);
    }
    public ServerResponse createBookList(BookList bookList){
        BookList bookList1=selectBookListByName(bookList.getBookList());
        if (bookList1!=null){
            throw new GlobalException(CodeMsg.BOOK_LIST_DUPLICATE);
        }
        bookList.setLikes(0);
        bookListMapper.insert(bookList);
        return ServerResponse.success("创建书单成功");
    }
}
