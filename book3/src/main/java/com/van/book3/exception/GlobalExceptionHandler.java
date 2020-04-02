package com.van.book3.exception;

import com.van.book3.common.CodeMsg;
import com.van.book3.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author Van
 * @date 2020/3/31 - 16:53
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ServerResponse exceptionHandler(Exception e ){
        e.printStackTrace();
        //if is BindException
        if (e instanceof BindException){
            BindException bindException=(BindException)e;
            List<ObjectError>  objectErrors= bindException.getAllErrors();
            List<String>msgList=new ArrayList<>();
            for (ObjectError objectError : objectErrors) {
                String msg=objectError.getDefaultMessage();
                msgList.add(msg);
            }
            return ServerResponse.error(CodeMsg.BIND_ERROR.fillArgs(msgList));
        }
        //if is GlobalException
        if (e instanceof GlobalException){
            GlobalException globalException=(GlobalException) e;
            return ServerResponse.error(globalException.getCodeMsg());
        }
//        if is ConstraintViolationException
        if (e instanceof ConstraintViolationException){
            ConstraintViolationException cve=(ConstraintViolationException)e;
            return ServerResponse.error(CodeMsg.BIND_ERROR.fillArgs(cve.getMessage()));
        }
        return ServerResponse.error("未知系统异常");
    }
}
