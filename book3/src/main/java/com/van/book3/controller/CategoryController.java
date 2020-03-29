package com.van.book3.controller;

import com.van.book3.common.ServerResponse;
import com.van.book3.dao.CategoryMapper;
import com.van.book3.serviceimpl.CategoryServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Van
 * @date 2020/3/16 - 12:37
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryServiceImpl categoryService;

    @RequestMapping("/list.do")
    public ServerResponse list() {
        return categoryService.list();
    }
}
