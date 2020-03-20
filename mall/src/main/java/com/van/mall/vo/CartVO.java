package com.van.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Van
 * @date 2020/3/12 - 12:43
 */
@Data
public class CartVO {
    private List<CartProductVO> cartProductVOLists;
    private Boolean allChecked;
    private String imageHost;
    private BigDecimal cartTotalPrice;
}
