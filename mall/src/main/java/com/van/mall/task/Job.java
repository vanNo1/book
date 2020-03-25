package com.van.mall.task;

import com.van.mall.util.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Van
 * @date 2020/3/25 - 11:26
 */
@Component
@Slf4j
public class Job {
    @Scheduled(cron = "*/5 * * * * ?")
    public void clean(){
        String time=Long.toString(System.currentTimeMillis()+5*1000);
        RedisLockUtil.lock("key",time);
      log.info("tomcat一执行清理垃圾111111111111111");
      RedisLockUtil.unLock("key",time);
    }
}
