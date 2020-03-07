package com.van.book3.utils;

import com.van.book3.vo.RegisterVO;

/**
 * @author Van
 * @date 2020/3/7 - 15:21
 */
public class RegisterVOUtil {
    public static RegisterVO success(){
        RegisterVO registerVO=new RegisterVO();
        registerVO.setError_code(0);
        registerVO.setMsg("用户注册成功");
        return registerVO;
    }
    public static RegisterVO error(){
        RegisterVO registerVO=new RegisterVO();
        registerVO.setError_code(1);
        registerVO.setMsg("用户注册失败");
        return registerVO;
    }
}
