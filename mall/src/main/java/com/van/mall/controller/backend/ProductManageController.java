package com.van.mall.controller.backend;

import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.entity.Product;
import com.van.mall.entity.User;
import com.van.mall.service.serviceImpl.FileServiceImpl;
import com.van.mall.service.serviceImpl.ProductServiceImpl;
import com.van.mall.service.serviceImpl.UserServiceImpl;
import com.van.mall.util.PropertiesUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/11 - 10:40
 */
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {
    @Resource
    private FileServiceImpl fileService;
    @Resource
    private UserServiceImpl userService;
    @Resource
    private ProductServiceImpl productService;
    @RequestMapping("/save.do")
    public ServerResponse productSave(HttpSession session, Product product){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("请先登录");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //to do logic
            return productService.saveOrUpdateProduct(product);

        }else {
            return ServerResponse.error("没有管理员权限");
        }
    }

    @RequestMapping("/set_sale_status.do")
    public ServerResponse setSaleStatus(HttpSession session ,@RequestParam Integer productId,@RequestParam Integer status){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("请先登录");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //to do logic
        return productService.setSaleStatus(productId,status);

        }else {
            return ServerResponse.error("没有管理员权限");
        }
    }

    @RequestMapping("/detail.do")
    public ServerResponse getDetail(HttpSession session , Integer productId){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("请先登录");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //to do logic
            return productService.manageProductDetail(productId);

        }else {
            return ServerResponse.error("没有管理员权限");
        }
    }

    @RequestMapping("/list.do")
    public ServerResponse getList(HttpSession session ,@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("请先登录");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //to do logic
            return productService.getProductList(pageNum, pageSize);

        }else {
            return ServerResponse.error("没有管理员权限");
        }
    }
    @RequestMapping(value = "/upload.do",method = RequestMethod.POST)
    public ServerResponse upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        String path=request.getSession().getServletContext().getRealPath("/upload");
        String targetFileName=fileService.upload(file,path);
        String url= PropertiesUtil.getPropertity("ftp.server.http.prefix")+targetFileName;
        Map fileMap=new HashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.success(fileMap);
    }

}
