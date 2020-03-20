package com.van.book3.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Van
 * @data 2020/3/15 - 11:43
 */
@Getter
//if these three member's value is null then key won't return to front-end
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private int error_code;
    private String msg;
    private T data;

    ServerResponse(int error_code,String msg,T data){
        this.data=data;
        this.error_code=error_code;
        this.msg=msg;
    }
    @JsonIgnore
    public  boolean isSuccess(){
        if (this.error_code==Const.ServerResponse.SUCCESS_CODE){
            return true;
        }else {
            return false;
        }
    }
    public static<T> ServerResponse<T> success(String msg,T data){
        return new ServerResponse(Const.ServerResponse.SUCCESS_CODE,msg,data);
    }
    public static <T>ServerResponse<T>error(String msg){
        return new ServerResponse(Const.ServerResponse.ERROR_CODE,msg,null);
    }
}
