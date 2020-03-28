package com.van.mall.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.dao.CartMapper;
import com.van.mall.dao.ProductMapper;
import com.van.mall.entity.Cart;
import com.van.mall.entity.Product;
import com.van.mall.service.ICartService;
import com.van.mall.util.BigDecimalUtil;
import com.van.mall.util.PropertiesUtil;
import com.van.mall.vo.CartProductVO;
import com.van.mall.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/12 - 11:51
 */
@Service
@Slf4j
public class CartServiceImpl implements ICartService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private ProductMapper productMapper;

    public void deleteById(int id) {
        cartMapper.deleteById(id);
    }

    public Cart selectByUserIdAndProductId(Integer userId, Integer productId) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("productId", productId);
        List<Cart> cartList = cartMapper.selectByMap(map);
        return cartList.get(0);
    }

    public List<Cart> selectCartListByUserId(Integer userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        List<Cart> carts = cartMapper.selectByMap(map);
        return carts;
    }

    //this method's function is return a user's cartvo, and automatic revised the product quantity by it's stock
    public CartVO getCartVOLimit(Integer userId) {
        CartVO cartVO = new CartVO();
        List<Cart> carts = selectCartListByUserId(userId);
        List<CartProductVO> cartProductVOS = new ArrayList<>();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(carts)) {
            for (Cart cart : carts
            ) {
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cart.getId());
                cartProductVO.setProductId(cart.getProductId());
                cartProductVO.setUserId(cart.getUserId());
                //find product
                Product product = productMapper.selectById(cart.getProductId());
                //suppose to think product is exist
                cartProductVO.setProductMainImage(product.getMainImage());
                cartProductVO.setProductName(product.getName());
                cartProductVO.setProductSubtitle(product.getSubtitle());
                cartProductVO.setProductStatus(product.getStatus());
                cartProductVO.setProductPrice(product.getPrice());
                cartProductVO.setProductStock(product.getStock());
                //judge stock
                int buyLimitCount = 0;
                if (product.getStock() >= cart.getQuantity()) {
                    buyLimitCount = cart.getQuantity();
                    cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);//stock is more than cart quantity
                } else {
                    buyLimitCount = product.getStock();
                    cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);//stock is less than cart quantity
                    //update cart quantity
                    Cart cartForQuantity = new Cart();
                    cartForQuantity.setQuantity(buyLimitCount);
                    cartForQuantity.setId(cart.getId());
                    cartMapper.updateById(cartForQuantity);
                    //.....................................
                }
                cartProductVO.setQuantity(buyLimitCount);
                //count total price for a product
                cartProductVO.setProductTotalPrice(BigDecimalUtil.mul(cartProductVO.getProductPrice().doubleValue(), cartProductVO.getQuantity().doubleValue()));
                cartProductVO.setProductChecked(cart.getChecked());//set checked
                if (cart.getChecked() == Const.Cart.CHECKED) {
                    //if this product is checked then add to a cart total price
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                }
                cartProductVOS.add(cartProductVO);//add cartProductVO to list
                cartVO.setCartProductVOLists(cartProductVOS);
                cartVO.setAllChecked(isAllChecked(userId));
                cartVO.setCartTotalPrice(cartTotalPrice);
                cartVO.setImageHost(PropertiesUtil.getPropertity("ftp.server.http.prefix"));
                return cartVO;

            }
        }
        return null;
    }

    //whether cart is all checked
    public boolean isAllChecked(Integer userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("checked", 0);
        List<Cart> carts = cartMapper.selectByMap(map);
        if (carts.size() > 0) {
            return false;//the product in this user's chart is not all checked
        } else {
            return true;//the product in this user's chart is all checked
        }
    }

    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        Cart cart = selectByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //have no record need to add
            Cart cartNew = new Cart();
            cartNew.setProductId(productId);
            cartNew.setUserId(userId);
            cartNew.setQuantity(count);
            cartMapper.insert(cartNew);

        } else {
            //have record need to add quantity
            int quntity = cart.getQuantity() + count;
            cart.setQuantity(quntity);
            cartMapper.updateById(cart);
        }
        CartVO cartVO = this.getCartVOLimit(userId);
        return ServerResponse.success(cartVO);
    }

    //update the count of the product of this user
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
        Cart cart = selectByUserIdAndProductId(userId, productId);
        cart.setQuantity(count);
        cartMapper.updateById(cart);
        return ServerResponse.success(getCartVOLimit(userId));
    }

    public ServerResponse delete(Integer userId, String productIds) {
        ArrayList<String> productIdList = Lists.newArrayList(productIds.split(","));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userId", userId);
        queryWrapper.in("productId", productIdList);
        cartMapper.delete(queryWrapper);
        return ServerResponse.success(getCartVOLimit(userId));
    }

    //if already selected all then Unselect all else select all
    public ServerResponse selectOrUnSelectAll(Integer userid) {
        if (isAllChecked(userid)) {
            //allSelected
            //todo unselect all
            Map map = new HashMap();
            map.put("userId", userid);
            List<Cart> cartList = cartMapper.selectByMap(map);
            // if this user's cart is empty
            if (cartList.size() == 0) {
                return ServerResponse.error("用户商品为空");
            }
            cartList.forEach(cartItem -> {
                cartItem.setChecked(0);
                cartMapper.updateById(cartItem);
            });
            return ServerResponse.success(getCartVOLimit(userid));

        } else {
            //not all selected
            //todo selected all
            Map map = new HashMap();
            map.put("userId", userid);
            List<Cart> cartList = cartMapper.selectByMap(map);
            // if this user's cart is empty
            if (cartList.size() == 0) {
                return ServerResponse.error("用户商品为空");
            }
            cartList.forEach(cartItem -> {
                cartItem.setChecked(1);
                cartMapper.updateById(cartItem);
            });
            return ServerResponse.success(getCartVOLimit(userid));
        }
    }

    //select one user's cart which is checked
    public List<Cart> selectUserCartWhichIsChecked(Integer userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("checked", 1);
        List<Cart> cartList = cartMapper.selectByMap(map);
        return cartList;
    }


}
