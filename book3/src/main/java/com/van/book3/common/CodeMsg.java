package com.van.book3.common;

import com.sun.javafx.binding.StringFormatter;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.awt.*;

/**
 * @author Van
 * @date 2020/3/31 - 16:58
 */
@Data
public class CodeMsg {
    private int code;
    private String msg;

    //general5001xx
    public static CodeMsg SYSTEM_ERROR=new CodeMsg(500100,"服务端异常");
    public static CodeMsg DB_ERROR=new CodeMsg(500102,"数据库操作异常");
    public static CodeMsg BIND_ERROR=new CodeMsg(500101,"参数校验异常%s");

    //login5002xx
    public static CodeMsg DUPLICATE_USER=new CodeMsg(500201,"用户重复注册");
    public static CodeMsg NOT_EXIST=new CodeMsg(500202,"用户不存在");
    public static CodeMsg INVALID=new CodeMsg(500203,"用户名或密码错误");
    public static CodeMsg NOT_LOGIN=new CodeMsg(500204,"用户未登录");
    public static CodeMsg WRONG_ANSWER=new CodeMsg(500205,"密保问题错误");
    public static CodeMsg TOKEN_EXPIRE=new CodeMsg(500206,"Token无效（超时或不正确）");
    public static CodeMsg SAME_PASSWORD=new CodeMsg(500207,"新旧密码不能相同");
    public static CodeMsg OPENID_FAIL=new CodeMsg(500208,"获取openID失败");

    //shelf5003xx
    public static CodeMsg SHELF_EMPTY=new CodeMsg(500301,"书架为空");
    public static CodeMsg SHELF_DUPLICATE=new CodeMsg(500302,"重复加入书架");
    public static CodeMsg SHELF_REMOVE_FAIL=new CodeMsg(500303,"未添加此书移出书架失败");
    public static CodeMsg SHELF_NOT_EXIST=new CodeMsg(500304,"书架无此书");

    //shelf5004xx
    public static CodeMsg BOOK_NOT_EXIST=new CodeMsg(500401,"书籍不存在");

    //rank5005xx
    public static CodeMsg RANK_SAVE_FAIL=new CodeMsg(500501,"评分保存失败");


    //bookList 5006xx
    public static CodeMsg BOOK_LIST_NOT_EXIST=new CodeMsg(500601,"书单不存在");
    public static CodeMsg BOOK_LIST_DUPLICATE=new CodeMsg(500602,"书单已存在");
    public static CodeMsg BOOK_LIST_BOOK_DUPLICATE=new CodeMsg(500603,"该书籍在该书单中已存在");
    public static CodeMsg BOOK_LIST_BOOK_NOT_EXIST=new CodeMsg(500603,"该书籍在该书单中不存在");


    CodeMsg(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public CodeMsg fillArgs(Object... args){
        int code=this.code;
        String msg= String.format(this.msg,args);
        return new CodeMsg(code,msg);
    }
}
