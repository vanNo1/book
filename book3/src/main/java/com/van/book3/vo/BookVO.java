package com.van.book3.vo;

import lombok.Data;

import javax.annotation.security.DenyAll;
import java.io.Serializable;
import java.util.List;

/**
 * @author Van
 * @date 2020/3/17 - 16:04
 */
@Data
public class BookVO implements Serializable {
    private int id;
    private String fileName;
    private String cover;
    private String title;
    private String author;
    private String publisher;
    private int category;
    private String categoryText;
    private String language;
    private String rootFile;
    private String opf;
    private List<ReaderVO> readers;//the readers who put this book in shelf
    private int readerNum;//who puts book in shelf
    private Integer rank;
    private int rankNum;
    private double rankAvg;
}
