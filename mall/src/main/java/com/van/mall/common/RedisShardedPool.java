package com.van.mall.common;

import com.van.mall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Van
 * @date 2020/3/22 - 22:24
 */
public class RedisShardedPool {
    private static ShardedJedisPool pool;//connection pool
    private static Integer maxTotal =Integer.parseInt(PropertiesUtil.getPropertity("redis.max.total")) ;//max connection
    private static Integer maxIdle =Integer.parseInt(PropertiesUtil.getPropertity("redis.max.idle"));//max idle connection
    private static Integer minIdle=Integer.parseInt(PropertiesUtil.getPropertity("redis.min.idle"));//min idle connection
    private static Boolean testOnBorrow=Boolean.parseBoolean(PropertiesUtil.getPropertity("redis.test.borrow"));//whether test when borrow a jedis if true then it's available
    private static Boolean testOnReturn=Boolean.parseBoolean(PropertiesUtil.getPropertity("redis.test.return"));//whether test when return a jedis if true then it's available
    private static String redis1Ip=PropertiesUtil.getPropertity("redis1.ip");
    private static Integer redis1Port=Integer.parseInt(PropertiesUtil.getPropertity("redis1.port")) ;
    private static String redis2Ip=PropertiesUtil.getPropertity("redis2.ip");
    private static Integer redis2Port=Integer.parseInt(PropertiesUtil.getPropertity("redis2.port")) ;
    private static void initPool(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
//whether block when resource is exhausted: false will throw a exception,true will block until timeout, default is true
        config.setBlockWhenExhausted(true);
        JedisShardInfo jedisShardInfo1=new JedisShardInfo(redis1Ip,redis1Port,1000*2);
        JedisShardInfo jedisShardInfo2=new JedisShardInfo(redis2Ip,redis2Port,1000*2);
        List<JedisShardInfo>jedisShardInfos=new ArrayList<>();
        jedisShardInfos.add(jedisShardInfo1);
        jedisShardInfos.add(jedisShardInfo2);
        pool=new ShardedJedisPool(config,jedisShardInfos, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }
    static {
        initPool();
    }
    public static ShardedJedis getJedis(){
        return pool.getResource();
    }
    public static void returnBrokenResource(ShardedJedis jedis){
        pool.returnBrokenResource(jedis);
    }
    public static void returnResource(ShardedJedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis jedis=getJedis();
        for (int i = 0; i < 10; i++) {
            jedis.set("key"+i,"value"+i);
        }
        returnResource(jedis);
    }

}
