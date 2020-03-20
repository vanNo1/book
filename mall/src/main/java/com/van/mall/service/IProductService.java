package com.van.mall.service;

import com.van.mall.common.ServerResponse;
import com.van.mall.entity.Product;
import com.van.mall.vo.ProductDetailVO;
import com.van.mall.vo.ProductListVO;

/**
 * @author Van
 * @date 2020/3/11 - 10:45
 */
public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);
    Boolean productIsExist(Integer productId);
    ServerResponse setSaleStatus(Integer productId,Integer status);
    ProductDetailVO assembleProductDetailVO(Product product);
    ServerResponse manageProductDetail(Integer productId);
    ProductListVO assembleProductListVO(Product product);
    ServerResponse getProductList(int pageNum,int pageSize);

}
