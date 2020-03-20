package com.van.mall.service.serviceImpl;

import com.van.mall.common.ServerResponse;
import com.van.mall.dao.CategoryMapper;
import com.van.mall.entity.Category;
import com.van.mall.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Van
 * @date 2020/3/10 - 19:22
 */
@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    public ServerResponse addCategory(String categoryName,Integer parentId){
        if (parentId==null||!StringUtils.isNotBlank(categoryName)){
            return ServerResponse.error("参数错误");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
       int count= categoryMapper.insert(category);
       if (count>0){
           return ServerResponse.success("添加商品成功");
       }else {
           return ServerResponse.error("插入失败");
       }

    }

    public ServerResponse setCategoryName(Integer categoryId,String categoryName){
        if (categoryId!=null&&StringUtils.isNotBlank(categoryName)){
            Category category=categoryMapper.selectById(categoryId);
            if (category!=null){
                category.setName(categoryName);
                int count=categoryMapper.updateById(category);
                if (count>0){
                    return ServerResponse.success("改名成功");
                }else {
                    return ServerResponse.error("插入失败");
                }
            }else {
                return  ServerResponse.error("无此类目");
            }
        }else {
            return ServerResponse.error("传入参数有误");
        }
    }

    public ServerResponse getChildrenParallelCategory(Integer categoryId){
        if (categoryId!=null){
            Map<String ,Object>map=new HashMap<>();
            map.put("parentId",categoryId);
            List<Category> categories=categoryMapper.selectByMap(map);
            if (categories.isEmpty()){
                log.info("为找到当前子分类");
            }
            return ServerResponse.success(categories);

        }
        return ServerResponse.error("参数错误");
    }

    public List<Category> selectCategoryChildrenByParentId(Integer parentId){
        Map<String,Object>map=new HashMap<>();
        map.put("parentId",parentId);
        return categoryMapper.selectByMap(map);
    }

    public ServerResponse getCategoryAndDeepChildrenCategory(Integer categoryId){
        Set<Category>categorySet=new HashSet<>();
        findChildCategory(categorySet,categoryId);//now set is full all children and itself
        if (categoryId!=null){
            List<Integer>categoryList=new ArrayList<>();
            categorySet.forEach(category -> {
                categoryList.add(category.getId());
            });
            return ServerResponse.success(categoryList);
        }
        return ServerResponse.error("参数错误");
    }
    //递归算法，算出子节点
    public Set<Category> findChildCategory(Set<Category>categorySet,Integer id){
        Category category=categoryMapper.selectById(id);
        categorySet.add(category);
        List<Category> categories=selectCategoryChildrenByParentId(id);
        if (categories.size()==0){
            return categorySet;
        }else {
            categories.forEach(categoryItem->{
                findChildCategory(categorySet,categoryItem.getId());
            });
        }
        return categorySet;
    }
}
