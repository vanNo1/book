package com.van.book3.utils;

import com.van.book3.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author Van
 * @date 2020/3/26 - 23:44
 */
@Slf4j
public class RedisPoolUtil {

    public static Long expire(String key, int exTime) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key{} exTime{} error", key, exTime, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //exTime is second
    public static String setEx(String key, String value, int exTime) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key{} value{} exTime{} error", key, value, exTime, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        return result;
    }

    public static String set(String key, String value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key{} value{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String get(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        return result;
    }

    public static Long del(String key) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
}
