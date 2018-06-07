package com.yingu.match.common.util;

import java.security.SecureRandom;

/**
 * 随机数实用类
 * @author liufei
 *
 */
public class RandomUtil {
	private static String tSpaceString="00000000";
    /**
     * 生成随机数字
     * @param aLength              随机数位数
     * @return                  生成的随机数
     */
    public static String genRandNum(int aLength) {
        SecureRandom    tRandom     = new SecureRandom();
        long            tLong;
        StringBuilder sb=new StringBuilder("");
        tRandom.nextLong();
        tLong = Math.abs(tRandom.nextLong());
        sb.append((String.valueOf(tLong)).trim());
        while (sb.length() < aLength){
            tLong = Math.abs(tRandom.nextLong());
            sb.append((String.valueOf(tLong)).trim());
        }
        String aString = sb.substring(0, aLength);
        
        return aString;
    }
    /**
     * 生成日期时间加指定位数的随机数
     * @param aBits             随机数位数
     * @return                  生成的随机数
     * @throws Exception 异常
     */
    public static String genDTRandNum(int aBits) throws Exception{
        String tCurDT = DateUtil.getCurrentTime();
        String tRs = "";
        tRs = tCurDT + genRandNum(aBits);
        return tRs;
    }
    /**
     * 生成22位随机数字
     * @return              YYMMDDhhmmSSsssRRRNNNN
     */
    public static String genDTRandNum(){
     /*   StringBuffer tSb = new StringBuffer();
        String tCurDT = "";
        tCurDT = DateUtil.getCurrentTime("DTM","AD2");
        tSb.append(tCurDT);
        tSb.append(genRandNum(3));
        int tHashCode = Thread.currentThread().hashCode();
        String tHCS = Integer.toString(tHashCode);
        tSb.append(tHCS.substring(tHCS.length() - 4));
        return tSb.toString();
        */
    	StringBuffer tSb = new StringBuffer();
        String tCurDT = "";
        tCurDT = DateUtil.getCurrentTime();
        tSb.append(tCurDT);
        tSb.append(genRandNum(3));
        int tHashCode = tSb.hashCode();
        String tHCS = Integer.toString(tHashCode);
        if(tHCS.length()<4){
        	tSb.append(tSpaceString.substring(0,4-tHCS.length()));
        	tSb.append(tHCS);
        }
        else{
        	tSb.append(tHCS.substring(tHCS.length() - 4));
        }
        return tSb.toString();
    }
    /**
     * 生成传入参数加14位的随机数
     * @param aCustom       传入参数
     * @return 传入参数加14位的随机数
     */
    public static String genCustomRandNum(String aCustom){
        StringBuffer tSb = new StringBuffer(aCustom);
        String tCurTime = "";
        tCurTime = DateUtil.getCurrentTime();
        tSb.append(tCurTime);
        int tHashCode = Thread.currentThread().hashCode();
        String tHCS = Integer.toString(tHashCode);
        tSb.append(tHCS.substring(tHCS.length() - 5));
        return tSb.toString();
    }
    /**
     * 生成密码.
     * @param count 密码位数
     * @param letters 是否包含字符
     * @param numbers 是否包含数字
     * @return String password
     */
    public static String genPassword(int count, boolean letters, boolean numbers) {
        return org.apache.commons.lang.RandomStringUtils.random(count, letters, numbers);
    }
    /**
     * 生成字符数字混合的密码.
     * @param count 密码位数
     * @return String password
     */
    public static String genPassword(int count) {
        return genPassword(count, true, true);
    }
    /**
     * 生成纯数字密码.
     * @param count 密码位数
     * @return String password
     */
    public static String genPasswordOfNumber(int count) {
        return genPassword(count, false, true);
    }
    /**
     * 生成纯字符密码.
     * @param count 密码位数
     * @return String password
     */
    public static String getPasswordOfCharacter(int count) {
        return genPassword(count, true, false);
    }
    
    public static void main(String[] args){
    	//System.out.println(RandomUtil.genPassword(8));
    	System.out.println("\u4e00-\u9fa5");
    }
}
