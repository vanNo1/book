package com.van.book3.vo;

import lombok.Data;

import javax.annotation.security.DenyAll;
import java.util.List;

/**
 * @author Van
 * @date 2020/4/2 - 15:08
 */
@Data
public class HomeVO {
    //category
    private List<CategoryVO> category;
    //recommend
    private List<BookSimplyVO> recommend;
    //hotbook
    private List<BookSimplyVO> hotBook;
    //freeread
    private List<BookSimplyVO> freeRead;
    //HotSearchVO
    private HotSearchVO hotSearch;

    private List<ShelfVO> shelf;
    private int shelfCount;

}
