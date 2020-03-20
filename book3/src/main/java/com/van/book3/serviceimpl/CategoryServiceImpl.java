package com.van.book3.serviceimpl;

import com.van.book3.common.ServerResponse;
import com.van.book3.dao.BookMapper;
import com.van.book3.dao.CategoryMapper;
import com.van.book3.entity.Book;
import com.van.book3.entity.Category;
import com.van.book3.service.CategoryService;
import com.van.book3.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/16 - 11:25
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private CategoryMapper categoryMapper;
    //find how much number of books a category contains
    public int findNumberOfCategroy(int categoryId){
        Map map=new HashMap();
        map.put("category",categoryId);
        List<Book>bookList=bookMapper.selectByMap(map);
        return bookList.size();
    }
    public ServerResponse<List<CategoryVO>> list(){
        Map map=new HashMap();
        List<Category>categoryList=categoryMapper.selectByMap(map);
        List<CategoryVO>categoryVOList=new ArrayList<>();
        for (Category categoryItem : categoryList) {
            CategoryVO categoryVO=new CategoryVO();
            categoryVO.setCover(categoryItem.getCover());
            categoryVO.setCategory(categoryItem.getCategory());
            categoryVO.setCategoryText(categoryItem.getCategoryText());
            categoryVO.setCover2(categoryItem.getCover2());
            categoryVO.setNum(findNumberOfCategroy(categoryItem.getCategory()));
            categoryVOList.add(categoryVO);
        }
        return ServerResponse.success("查询成功",categoryVOList);
    }
}
