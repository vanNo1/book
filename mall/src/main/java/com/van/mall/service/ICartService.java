package com.van.mall.service;

import com.van.mall.common.ServerResponse;
import com.van.mall.entity.Cart;
import com.van.mall.vo.CartVO;

import java.util.List;

/**
 * @author Van
 * @date 2020/3/12 - 11:51
 */
public interface ICartService {
    Cart selectByUserIdAndProductId(Integer userId, Integer productId);

    ServerResponse add(Integer userId, Integer productId, Integer count);

    List<Cart> selectCartListByUserId(Integer userId);

    CartVO getCartVOLimit(Integer userId);

    boolean isAllChecked(Integer userId);

    ServerResponse update(Integer userId, Integer productId, Integer count);

    ServerResponse delete(Integer userId, String productIds);

    ServerResponse selectOrUnSelectAll(Integer userid);

    List<Cart> selectUserCartWhichIsChecked(Integer userId);
}
