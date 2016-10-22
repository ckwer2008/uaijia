package com.uaijia.core.util;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClient {
	
	final static Map<Integer,JedisPool> pools = new HashMap<Integer,JedisPool>();
	
	private String host;
	private int port;
	private String password;
	private int timeout;
	private int database;
	private JedisPoolConfig jedisPoolConfig;
	private Jedis jedis;


	public void init(){
		JedisPool pool = new JedisPool(jedisPoolConfig,host,port,timeout,password,database);
		jedis = pool.getResource();
	}


	public String get(final String key){
		return jedis.get(key);
	}




	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}
}
