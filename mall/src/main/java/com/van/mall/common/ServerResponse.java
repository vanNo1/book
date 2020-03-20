package com.van.mall.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Van
 * @date 2020/3/9 - 16:27
 */
@Getter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//如果是null的对象，key也会消失，不要返回给前端
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;
    private ServerResponse(int status,String msg,T data){
        this.data=data;
        this.msg=msg;
        this.status=status;
    }
    public boolean isSuccess(){
        if (this.status==1)
        {
            return false;
        }else{
            return true;
        }
    }
    public static<T> ServerResponse<T> success(T data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),null,data);
    }
    public static ServerResponse error(String msg){
        return new ServerResponse(ResponseCode.ERROR.getCode(),msg,null);
    }
}
