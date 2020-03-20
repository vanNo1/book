package com.van.book3.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Van
 * @date 2020/3/16 - 12:28
 */
@Data
public class CategoryVO implements Serializable {
    private String cover;
    private Integer category;
    private String categoryText;
    private Integer num;
    private String cover2;
}
