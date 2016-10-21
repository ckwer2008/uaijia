package com.uaijia.core.util;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public abstract class JedisUtils {
	
	public static final int REDIS_DATABASE;		//测试库
	
	final static Map<Integer,JedisPool> pools = new HashMap<Integer,JedisPool>();
	
	static String host;
	
	static String sPort;
	static int port;
	
	static String password;
	
	static String sTimeout;
	static int timeout;
	
	static JedisPoolConfig jedisPoolConfig;
	
	static{
		String datebaseIndex = PropertiesUtils.getProperty("redis.datebase");
		REDIS_DATABASE = Integer.parseInt(datebaseIndex);
		
		host = PropertiesUtils.getProperty("redis.host");
		sPort = PropertiesUtils.getProperty("redis.port");
		port = 6379;
		if(!StringUtils.isEmpty(sPort) && StringUtils.isNumeric(sPort)){
			port = StringUtils.stringToInteger(sPort);
		}
		
		sTimeout = PropertiesUtils.getProperty("redis.timeout");
		timeout = 2000;
		if(!StringUtils.isEmpty(sTimeout) && StringUtils.isNumeric(sTimeout)){
			timeout = StringUtils.stringToInteger(sTimeout);
		}
		
		password = PropertiesUtils.getProperty("redis.password");
		if(StringUtils.isEmpty(password)){
			password = null;
		}
		
		jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setTestOnBorrow(true);
	}
	
	public static Jedis getJedis(int database){
		JedisPool pool = pools.get(database);
		if(pool == null){
			pool = new JedisPool(jedisPoolConfig,host,port,timeout,password,database);
			pools.put(database, pool);
		}
		
		Jedis jedis = pool.getResource();
		return jedis;
	}
	
	public static void returnResource(int database,Jedis jedis){
		JedisPool pool = pools.get(database);
		
		pool.returnResource(jedis);
	}
	
}
