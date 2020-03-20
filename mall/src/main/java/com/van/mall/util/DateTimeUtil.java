package com.van.mall.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Van
 * @date 2020/3/11 - 15:09
 */
public class DateTimeUtil {
    public static final String FORMATSTR="yyyy-MM-dd hh:mm:ss";
    public static String  DateToStr(LocalDateTime dateTime, String formatStr){
        if (dateTime==null){
            return StringUtils.EMPTY;
        }
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern(formatStr);
        return dateTime.format(dateTimeFormatter);
    }
    public static String  DateToStr(LocalDateTime dateTime){
        if (dateTime==null){
            return StringUtils.EMPTY;
        }
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern(DateTimeUtil.FORMATSTR);
        return dateTime.format(dateTimeFormatter);
    }
    public static LocalDateTime StrToDate(String dateTime,String formatStr){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern(formatStr);
        return LocalDateTime.parse(dateTime,dateTimeFormatter);
    }
    public static LocalDateTime StrToDate(String dateTime){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern(DateTimeUtil.FORMATSTR);
        return LocalDateTime.parse(dateTime,dateTimeFormatter);
    }


}
