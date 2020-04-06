package com.van.book3.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.van.book3.common.CodeMsg;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.BookList2Mapper;
import com.van.book3.entity.Book;
import com.van.book3.entity.BookList;
import com.van.book3.entity.BookList2;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.BookList2Service;
import com.van.book3.utils.AssembleVOUtil;
import com.van.book3.vo.BookListVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/4/5 - 14:27
 */
@Service
public class BookList2ServiceImpl implements BookList2Service {
    @Resource
    private AssembleVOUtil assembleVOUtil;
    @Resource
    private BookServiceImpl bookService;
    @Resource
    private BookList2Mapper bookList2Mapper;
    @Resource
    private BookListServiceImpl bookListService;
    public ServerResponse addBook(String bookList,String fileName,String openId){
        //BookList must exist
     BookList bookList1= bookListService.selectBookListByName(bookList);
     if (bookList1==null){
         throw new GlobalException(CodeMsg.BOOK_LIST_NOT_EXIST);
     }
        //Book must exist
        Book book =bookService.selectBookByFileName(fileName);
     if (book==null){
         throw new GlobalException(CodeMsg.BOOK_NOT_EXIST);
     }
     //can add repeat
        Map map=new HashMap();
        map.put("book_list",bookList);
        map.put("file_name",fileName);
        List<BookList2> bookList2s=bookList2Mapper.selectByMap(map);
        if (bookList2s.size()!=0){
            throw new GlobalException(CodeMsg.BOOK_LIST_BOOK_DUPLICATE);
        }
        BookList2  bookList2=new BookList2();
        bookList2.setOpenId(openId);
        bookList2.setBookList(bookList);
        bookList2.setFileName(fileName);
        bookList2Mapper.insert(bookList2);
        return  ServerResponse.success("在书单中添加书籍成功");
    }
    public ServerResponse deleteBook(String bookList,String fileName,String openId){
        Map map=new HashMap();
        map.put("book_list",bookList);
        map.put("file_name",fileName);
        map.put("open_id",openId);
        List<BookList2> bookList2s=bookList2Mapper.selectByMap(map);
        if (bookList2s.size()==0){
            throw new GlobalException(CodeMsg.BOOK_LIST_BOOK_NOT_EXIST);
        }
        bookList2Mapper.deleteById(bookList2s.get(0).getId());
        return ServerResponse.success("删除成功");
    }
    public ServerResponse showBookList(String bookList,int current,int pageSize){
        Map map=new HashMap();
        map.put("book_list",bookList);
        BookList bookList1=bookListService.selectBookListByName(bookList);
        if (bookList1==null){
            throw new GlobalException(CodeMsg.BOOK_LIST_NOT_EXIST);
        }
        QueryWrapper  queryWrapper=new QueryWrapper();
        queryWrapper.eq("book_list",bookList);
        Page<BookList2>bookList2Page=new Page<>(current,pageSize);
        IPage<BookList2>iPage=bookList2Mapper.selectPage(bookList2Page,queryWrapper);
        List<BookList2>bookList2s= iPage.getRecords();
        List<String>fileNameList=new ArrayList<>();
        //collect all the book's name which in the book_list2
        for (BookList2 bookList2 : bookList2s) {
            fileNameList.add(bookList2.getFileName());
        }
        //use these book's name to select books
        List<Book> bookList2=bookService.selectBooks(fileNameList);
        //assemble them
       BookListVO bookListVOS= assembleVOUtil.assembleBookListVO(bookList2,bookList1);
       return ServerResponse.success("查询成功",bookListVOS);
    }
}
