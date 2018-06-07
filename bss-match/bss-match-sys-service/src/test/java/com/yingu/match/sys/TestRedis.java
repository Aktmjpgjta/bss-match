package com.yingu.match.sys;

import redis.clients.jedis.Jedis;

public class TestRedis {

	public static void main(String[] args) {
		
		Jedis jedis = new Jedis("127.0.0.1", 6379);
	//	jedis.auth("123456");
		System.out.println(jedis);  
        String ping = jedis.ping();  
        System.out.println(ping);
		
	}
	
}
