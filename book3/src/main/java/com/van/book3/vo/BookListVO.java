package com.van.book3.vo;

import com.van.book3.entity.Book;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Van
 * @date 2020/4/5 - 15:43
 */
@Data
public class BookListVO {
    //user
    private String openId;
    private String bookList;
    //bookList
    private String title;
    private int likes;
    //book
    private List<BookSimplyVO> books;
}
