package com.van.book3.common;

/**
 * @author Van
 * @date 2020/3/15 - 11:56
 */
public class Const {
    public static final String CACHE_CATEGORY="categoryCache";
    public static final String TOKEN_PREFIX="token_";
    public static final String CURRENT_USER = "CURRENT_USER";
    public static final String DOMAIN = "http://store.yangxiansheng.top/";
    public static final Integer MAXBOOKS = 500;
    public static final Integer DUPLICATE = 11;//some key is duplicate
    public static final Integer ISNULL = 5;//some param is null
    public static final Integer SYSTEMERROR = -1;//system error

    public interface ServerResponse {
        public static final int SUCCESS_CODE = 0;
        public static final int ERROR_CODE = -1;
    }

}
