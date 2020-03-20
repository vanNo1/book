package com.van.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Van
 * @date 2020/3/12 - 12:12
 */
@Data
public class CartProductVO {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity; //this product quantity in cart
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStock;
    private  Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productChecked;//whether product selected
    private String limitQuantity;//a limit quantity result
}
