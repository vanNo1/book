package com.van.book3.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Van
 * @date 2020/3/26 - 23:40
 */
@Slf4j
public class PropertiesUtil {
    private static Properties properties;

    static {
        String fileName = "application.properties";
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(org.apache.logging.log4j.util.PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)));
        } catch (IOException e) {
            log.error("配置文件读取异常", e);
        }

    }

    public static String getPropertity(String key) {
        String value = properties.getProperty(key.trim());
        if (StringUtils.isNotBlank(value)) {
            return value.trim();
        }
        return null;
    }

    public static String getPropertity(String key, String defaultValue) {
        String value = properties.getProperty(key.trim());
        if (!StringUtils.isNotBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }
}
