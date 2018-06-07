package com.yingu.match.common.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

/**
 * 编码或加密实用类
 * @author 刘霏
 *
 */
public class EncryptUtil {
	private static Logger log = Logger.getLogger(EncryptUtil.class);
	private EncryptUtil(){}
	
	/**
	 * 以MD5的形式加密
	 * 失败时返回源字符串
	 * @param src
	 * @return
	 */
	public static String md5(String src){
		String rtn = src;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			return byteToHexString(md.digest());
		} catch (Exception e) {
			log.error(e.toString());
		}
		return rtn;
	}
	
	public static String encryptMd5(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data);

		return byteToHexString(md.digest());
	}
	
	public static String desEnc(String src, String key){
		return null;
	}
	/**
	 * 生成随机整数
	 * @return
	 */
	public static Integer genRandomInt(int len){
		double length = Math.pow(10, len);
		Integer rdInt = (int)(length*Math.random());
		
		return rdInt;
	}
	
	
	private static String byteToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String temp = "";
		for (int i = 0; i < b.length; ++i) {
			temp = Integer.toHexString(b[i] & 0xFF);
			if (temp.length() == 1)
				temp = "0" + temp;
			sb.append(temp);
		}
		return sb.toString();
	}
	
}
