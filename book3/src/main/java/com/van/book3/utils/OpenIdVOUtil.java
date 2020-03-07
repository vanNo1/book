package com.van.book3.utils;

import com.van.book3.entity.Openid;
import com.van.book3.vo.OpenIdVO;
import lombok.Data;

/**
 * @author Van
 * @date 2020/3/7 - 10:31
 */
@Data
public class OpenIdVOUtil<T> {
//    private int error_code;
//    private String msg;
//    private T data;
    public static OpenIdVO success(Openid openid){
        OpenIdVO<Openid> openIdVO=new OpenIdVO<>();
        openIdVO.setError_code(0);
        openIdVO.setMsg("success");
        openIdVO.setData(openid);
        return  openIdVO;
    }
    public static OpenIdVO error(){
        OpenIdVO<Openid> openIdVO=new OpenIdVO<>();
        openIdVO.setError_code(1);
        openIdVO.setMsg("error");
        return openIdVO;
    }
}
