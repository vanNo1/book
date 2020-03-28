package com.van.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Van
 * @date 2020/3/11 - 11:38
 */
@Data
public class ProductDetailVO {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;

    private String imageHost;
    private Integer parentCategoryId;
}
