package com.van.book3.service;

import com.van.book3.common.ServerResponse;
import com.van.book3.vo.CategoryVO;

import java.util.List;

/**
 * @author Van
 * @date 2020/3/16 - 11:24
 */
public interface CategoryService {
    int findNumberOfCategroy(int categoryId);

    ServerResponse<List<CategoryVO>> list();
}
