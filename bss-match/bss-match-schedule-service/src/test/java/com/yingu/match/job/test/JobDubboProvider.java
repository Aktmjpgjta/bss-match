package com.yingu.match.job.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author hc
 *
 */
public class JobDubboProvider {
	
	private static final Log log = LogFactory.getLog(JobDubboProvider.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("== DubboProvider context start error:",e);
		}
		synchronized (JobDubboProvider.class) {
			while (true) {
				try {
					JobDubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:",e);
				}
			}
		}
	}
    
}