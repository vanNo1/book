package com.van.mall.common;

import com.van.mall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Van
 * @date 2020/3/25 - 18:58
 */
@Component
@Slf4j
public class RedissonManager {
    private Config config =new Config();
    private Redisson redisson=null;
    private static String redisIp= PropertiesUtil.getPropertity("redis1.ip");
    private static Integer redisPort=Integer.parseInt(PropertiesUtil.getPropertity("redis1.port")) ;

    //构造方法完成后自动执行该方法
    @PostConstruct
    private void init(){
        try {
            //Address 是ip:port的形式
            config.useSingleServer().setAddress(new StringBuilder().append(redisIp).append(":").append(redisPort).toString());
            redisson=(Redisson) Redisson.create(config);
            log.info("初始化Redisson完成");
        } catch (Exception e) {
            log.error("初始化Redisson失败",e);
        }
    }
    public Redisson getRedisson(){
        return redisson;

    }
}
