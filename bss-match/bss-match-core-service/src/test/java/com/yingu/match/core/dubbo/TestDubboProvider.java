package com.yingu.match.core.dubbo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author hc
 *
 */
public class TestDubboProvider {
	
	private static final Log log = LogFactory.getLog(TestDubboProvider.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
			context.start();
		} catch (Exception e) {
			log.error("== DubboProvider context start error:",e);
		}
		synchronized (TestDubboProvider.class) {
			while (true) {
				try {
					TestDubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:",e);
				}
			}
		}
	}
    
}