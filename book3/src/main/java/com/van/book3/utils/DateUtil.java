package com.van.book3.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Van
 * @date 2020/3/7 - 14:18
 */
public class DateUtil {
    public static long now() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String nowString = simpleDateFormat.format(date);
        return Long.valueOf(nowString);
    }
}
