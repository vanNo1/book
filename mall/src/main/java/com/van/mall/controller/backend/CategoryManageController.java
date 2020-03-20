package com.van.mall.controller.backend;

import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.entity.User;
import com.van.mall.service.serviceImpl.CategoryServiceImpl;
import com.van.mall.service.serviceImpl.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Van
 * @date 2020/3/10 - 18:32
 */
@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {
    @Resource
    private CategoryServiceImpl categoryService;
    @Resource
    private UserServiceImpl userService;
    @RequestMapping("/add_category.do")
    public ServerResponse addCategory(HttpSession session, @RequestParam String categoryName,@RequestParam(defaultValue = "0") int parentId){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("需要登录");
        }
        //check user if a admin
        ServerResponse response= userService.checkAdminRole(user);
        if (response.isSuccess()){
            //he is a admin
            //add logical code
            return categoryService.addCategory(categoryName,parentId);
        }else {
            return ServerResponse.error("不是管理员，无权限");
        }
    }

    @RequestMapping("/set_category_name.do")
    public ServerResponse setCategoryName(HttpSession session,@RequestParam Integer categoryId,@RequestParam String categoryName){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("需要登录");
        }
        //check user if a admin
        ServerResponse response= userService.checkAdminRole(user);
        if (response.isSuccess()){
            //to do reset category name
           return categoryService.setCategoryName(categoryId, categoryName);
        }else {
            return ServerResponse.error("不是管理员，无权限！");
        }

    }

    @RequestMapping("/get_category.do")
    public ServerResponse getChildrenParallelCategory(@RequestParam Integer parentId,HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("需要登录");
        }
        //check user if a admin
        ServerResponse response= userService.checkAdminRole(user);
        if (response.isSuccess()){
            return categoryService.getChildrenParallelCategory(parentId);
        }else {
            return ServerResponse.error("不是管理员，无权限！");
        }
    }


    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(defaultValue = "0") Integer categoryId){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("需要登录");
        }
        //check user if a admin
        ServerResponse response= userService.checkAdminRole(user);
        if (response.isSuccess()){
            return categoryService.getCategoryAndDeepChildrenCategory(categoryId);
        }else {
            return ServerResponse.error("不是管理员，无权限！");
        }

    }

}
