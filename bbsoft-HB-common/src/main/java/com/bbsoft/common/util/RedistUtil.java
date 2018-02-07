package com.bbsoft.common.util;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class RedistUtil{
	
	public static JedisConnectionFactory jedisConnectionFactory(){
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("192.168.1.28");
		connectionFactory.setPort(6379);
		connectionFactory.setPassword("diaoni123");
		return connectionFactory;
	}
	
	public static void main(String[] args) {
		JedisConnectionFactory jedisConn = jedisConnectionFactory();
		jedisConn.getConnection();
	}
}
