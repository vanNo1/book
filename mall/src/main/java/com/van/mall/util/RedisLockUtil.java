package com.van.mall.util;

import com.van.mall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author Van
 * @date 2020/3/25 - 15:39
 */
@Slf4j
public class RedisLockUtil {
    public static Boolean lock(String key,String value){
        Jedis jedis= RedisPool.getJedis();
        if (jedis.setnx(key,value).intValue()==1){
            log.info("抢到锁了");
            return true;//加锁成功
        }
        //现在还不能因为没取到锁就直接返回false，得判断这个锁是否过期。防止有线程死在里面没有解锁，后面的线程就永远拿不到锁了
        String lastTime=jedis.get(key);
        if (Long.valueOf(lastTime)<System.currentTimeMillis()){
            String lastTime2=jedis.getSet(key,value);
            //如果此时有两个线程同时判断了时间超时，并且到了要修改value这一步，这个判断保证了只能有一个线程获得锁
            //ps：后面getset的线程虽然拿不到锁了，但是它改了value，不过不要紧，这两个同时到达这里的线程。value不可能相差大于毫秒
            if (lastTime2.equals(lastTime)){
                log.info("抢到锁了");
                return true;//加锁成功
            }
        }
        log.info("没抢到锁啊");
        return false;
    }
    //传进来的key和value和加锁的时候传进来的key和value是一样的
    public static void unLock(String key,String value){
        Jedis jedis=RedisPool.getJedis();
        //获得redis中目前key对应的value，如果是加锁的这个线程set的，那么就有权限解锁
        //ps：防止死了的线程活过来，来了个解锁操作
        String MyTime=jedis.get(key);
        if (value.equals(MyTime)){
            jedis.del(key);
            log.info("解锁。。。");
        }
    }
}
