package com.van.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Van
 * @date 2020/3/11 - 17:26
 */
@Data
public class ProductListVO {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private BigDecimal price;
    private Integer status;
    private String imageHost;

}
