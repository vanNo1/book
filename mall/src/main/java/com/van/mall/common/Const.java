package com.van.mall.common;

import lombok.Getter;

/**
 * @author Van
 * @date 2020/3/9 - 21:20
 */
public class Const {
    public static final String CURRENT_USER="currentUser";
    public static final String USERNAME="username";
    public static final String EMAIL="email";
    public interface ReidsCacheExTime{
        int REDIS_SESSION_EXTIME=60*30;//30min
    }
    public interface Role{
        int ROLE_CUSTOMER=0;//normal user
        int ROLE_ADMIN=1;//admin
    }
    public interface Cart{
        int CHECKED=1;//
        int UN_CHECKED=0;

        String LIMIT_NUM_SUCCESS="LIMIT_NUM_SUCCESS";
        String LIMIT_NUM_FAIL="LIMIT_NUM_FAIL";

    }
    @Getter
    public enum  OrderStatusEnum{
        CANCLED(0,"cancled"),
        NO_PAY(10,"no pay"),
        PAID(20,"paid"),
        SHIPPED(40,"already shipped"),
        ORDER_SUCCESS(50,"order is success"),
        ORDER_CLOSED(60,"order is closed")
        ;
        private Integer code;
        private String value;
        OrderStatusEnum(Integer code,String value){
            this.code=code;
            this.value=value;
        }

    }

    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY="WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS="TRADE_SUSSESS";
        String RESPONSE_SUCCESS="success";
        String RESPONSE_FAIL="fail";

    }

}
