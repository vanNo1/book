package com.van.mall.task;

import com.van.mall.common.RedissonManager;
import com.van.mall.util.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Van
 * @date 2020/3/25 - 11:26
 */
@Component
@Slf4j
public class Job {
    @Resource
    private RedissonManager redissonManager;
//    @Scheduled(cron = "*/5 * * * * ?")
    public void clean(){
        String time=Long.toString(System.currentTimeMillis()+5*1000);
        RedisLockUtil.lock("key",time);
      log.info("tomcat一执行清理垃圾111111111111111");
      RedisLockUtil.unLock("key",time);
    }
    @Scheduled(cron = "*/5 * * * * ?")
    public void clean2(){
        RLock lock= redissonManager.getRedisson().getLock("key");
        try {
            if (lock.tryLock(2,5, TimeUnit.SECONDS)) {
                log.info("tomcat一执行清理垃圾111111111111111");
            }else {
                log.info("Redisson没获取到锁:ThreadName:{}",Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redisson获取锁异常",e);
        }finally {
            lock.unlock();
            log.info("Redisson释放锁");
        }
    }
}
