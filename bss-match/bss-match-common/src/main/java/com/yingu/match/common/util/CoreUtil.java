package com.yingu.match.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * 内核实用方法
 * @author liufei
 *
 */
public class CoreUtil {

	private static Logger log = Logger.getLogger(CoreUtil.class);
	private CoreUtil(){}
	
	/**
	 * 生成排序号
	 * @return
	 */
	public static Long generateSortIdx(){
		return System.currentTimeMillis();
	}
	/**
	 * 生成当前日间戳
	 * @return
	 */
	public static Timestamp generateTimestamp(){
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	/**
	 * 生成字符串型的UUID
	 * @return
	 */
	public static String genUUIDString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	/**
	 * 
	 * Description: 关闭资源
	 * @param
	 * @return void
	 * @throws
	 * @Author liufei
	 * Create Date: 2015年5月13日 下午3:59:40
	 */
	public static void closeStream(Closeable closeable){
		if(null!=closeable){
			try {
				closeable.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}
	
}
