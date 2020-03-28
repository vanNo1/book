package com.van.mall.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.van.mall.common.ServerResponse;
import com.van.mall.dao.CategoryMapper;
import com.van.mall.dao.ProductMapper;
import com.van.mall.entity.Category;
import com.van.mall.entity.Product;
import com.van.mall.service.IProductService;
import com.van.mall.util.DateTimeUtil;
import com.van.mall.util.PropertiesUtil;
import com.van.mall.vo.ProductDetailVO;
import com.van.mall.vo.ProductListVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Van
 * @date 2020/3/11 - 10:45
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CategoryMapper categoryMapper;

    public ServerResponse saveOrUpdateProduct(Product product) {
        //if it's qualify
        if (product == null) {
            return ServerResponse.error("商品为空");
        }
        if (product.getCategoryId() == null || product.getName() == null || product.getPrice() == null || product.getStock() == null) {
            return ServerResponse.error("商品参数错误");
        }
        //if the product is exist
        if (StringUtils.isNotBlank(product.getSubImages())) {
            String[] subImageArray = product.getSubImages().split(",");
            if (subImageArray.length > 0)
                product.setMainImage(subImageArray[0]);
        }
        if (product.getId() != null) {
            //update product
            int count = productMapper.updateById(product);
            if (count > 0) {
                return ServerResponse.success("更新产品成功");
            } else {
                return ServerResponse.error("更新产品失败");
            }

        } else {
            //insert product
            int count = productMapper.insert(product);
            if (count > 0) {
                return ServerResponse.success("插入产品成功");
            } else {
                return ServerResponse.error("更新产品失败");
            }

        }
    }

    public Boolean productIsExist(Integer productId) {
        if (productId == null) {
            return false;
        }
        Product product = productMapper.selectById(productId);
        //if product is not exist
        if (product == null) {
            return false;
        } else {
            return true;
        }

    }

    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.error("参数错误");
        }
        //if product is exist
        if (productIsExist(productId)) {
            //set it status
            Product product = productMapper.selectById(productId);
            product.setStatus(status);
            int count = productMapper.insert(product);
            if (count > 0) {
                return ServerResponse.success("修改产品状态成功");
            } else {
                return ServerResponse.error("修改产品状态失败");
            }
        } else {
            //product is not exist
            return ServerResponse.error("产品不存在");
        }
    }

    public ProductDetailVO assembleProductDetailVO(Product product) {
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setName(product.getName());
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setId(product.getId());

        productDetailVO.setImageHost(PropertiesUtil.getPropertity("ftp.server.http.prefix", "http://img.happy.mmall.com/"));

        Category category = categoryMapper.selectById(productDetailVO.getId());
        if (category == null) {
            productDetailVO.setParentCategoryId(0);//default is a root node
        }
        productDetailVO.setParentCategoryId(category.getParentId());
        productDetailVO.setCreateTime(DateTimeUtil.DateToStr(product.getUpdateTime()));
        productDetailVO.setUpdateTime(DateTimeUtil.DateToStr(product.getUpdateTime()));

        return productDetailVO;


    }

    public ProductListVO assembleProductListVO(Product product) {
        ProductListVO vo = new ProductListVO();
        vo.setCategoryId(product.getCategoryId());
        vo.setId(product.getId());
        vo.setMainImage(product.getMainImage());
        vo.setName(product.getName());
        vo.setPrice(product.getPrice());
        vo.setStatus(product.getStatus());
        vo.setSubtitle(product.getSubtitle());
        vo.setImageHost(PropertiesUtil.getPropertity("ftp.server.http.prefix", "http://img.happy.mmall.com/"));
        return vo;
    }

    public ServerResponse manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.error("传入参数有误");
        }
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return ServerResponse.error("商品不存在");
        }
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);
        return ServerResponse.success(productDetailVO);
    }

    public ServerResponse getProductList(int pageNum, int pageSize) {
        List<ProductListVO> productListVOList = new ArrayList<>();
        Page<Product> productPage = new Page<>(pageNum, pageSize, false);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        IPage<Product> iPage = productMapper.selectPage(productPage, queryWrapper);
        List<Product> productList = iPage.getRecords();
        productList.forEach(product -> {
            ProductListVO productListVO = assembleProductListVO(product);
            productListVOList.add(productListVO);
        });
        return ServerResponse.success(productListVOList);
    }


}
