package com.van.book3.common;


import com.van.book3.utils.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Van
 * @date 2020/3/26 - 23:40
 */
public class RedisPool {

    private static JedisPool pool;//connection pool
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getPropertity("redis.max.total"));//max connection
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getPropertity("redis.max.idle"));//max idle connection
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getPropertity("redis.min.idle"));//min idle connection
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getPropertity("redis.test.borrow"));//whether test when borrow a jedis if true then it's available
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getPropertity("redis.test.return"));//whether test when return a jedis if true then it's available
    private static String redisIp = PropertiesUtil.getPropertity("redis1.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getPropertity("redis1.port"));

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
//whether block when resource is exhausted: false will throw a exception,true will block until timeout, default is true
        config.setBlockWhenExhausted(true);
        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis) {
        returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis) {
        returnResource(jedis);
    }

}
