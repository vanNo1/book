package com.van.book3.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Van
 * @date 2020/4/4 - 19:10
 */
@Data
public class BookVO2 extends BookSimplyVO{
    //hot_book
    private int readerNum;//how many people have have read this book before(hot_book)
    //rank
    private int rankNum;//how many people have rank this book
    private double rankAvg;
    //introduction
    private String content;
    private String authorIntroduction;
    //review
    private List<ReviewVO>reviewVOS;

}
