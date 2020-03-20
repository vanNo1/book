package com.van.mall.service;

import com.van.mall.common.ServerResponse;
import com.van.mall.entity.Category;

import java.util.List;
import java.util.Set;

/**
 * @author Van
 * @date 2020/3/10 - 19:15
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse setCategoryName(Integer categoryId,String categoryName);
    ServerResponse getChildrenParallelCategory(Integer categoryId);
    List<Category> selectCategoryChildrenByParentId(Integer parentId);
    ServerResponse getCategoryAndDeepChildrenCategory(Integer categoryId);
    Set<Category> findChildCategory(Set<Category>categorySet, Integer id);
}
