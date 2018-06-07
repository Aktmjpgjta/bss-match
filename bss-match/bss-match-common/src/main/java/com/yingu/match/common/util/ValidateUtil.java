package com.yingu.match.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * 
 * 
 * Description: 验证工具类
 * 
 * @author liufei
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2013-11-15    liufei       1.0        1.0 Version
 * </pre>
 */
public class ValidateUtil {
	private static final String REG_USERNAME = "^[a-zA-Z]\\w{3,31}";//4-32位
	private static final String REG_NAME = "[a-zA-Z0-9_\u4e00-\u9fa5]{4,32}";
	private static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	//private static final String REG_PASSWORD = "(?-i)(?=^.{6,}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z]))(?=(1)(?=.*\\d)|.*[^A-Za-z0-9])^.*$";
//	private static final String REG_PASSWORD = "^[a-z0-9A-Z~!@#$%^&*]{6,32}$";
	private static final String REG_PASSWORD = "(?=^.{6,32}$)(?=.*\\d)(?=.*[a-zA-Z])(?!.*\n).*$";
	private static final String AMOUNT_STRING = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"; // 金额校验表达式，小数点后两位
	private static final String CHECK_NUMBER = "^[0-9]*[1-9][0-9]*$"; // 验证数字为正整数

	/**
	 * 验证必填项
	 * 
	 * @param dataObj
	 * @param propNames
	 * @param displayNames
	 * @return errorList
	 * @throws Exception
	 */
	public static List<String> validateMust(Object dataObj, String[] propNames,
			String[] displayNames) throws Exception {
		Assert.isTrue(propNames.length == displayNames.length, "参数名与显示名的个数不一致");
		List<String> errorList = new ArrayList<String>();
		for (int i = 0; i < propNames.length; i++) {
			String propName = propNames[i];
			String displayName = displayNames[i];
			Object value = ModelUtil.getPropertyValue(dataObj, propName);
			if (null == value) {
				errorList.add(displayName + "(" + propName + ")是必填项");
			} else {
				if (value instanceof String) {
					// 处理全角空格
					String valueStr = value.toString().replace("　", "").trim();
					if (valueStr.length() == 0) {
						errorList.add(displayName + "(" + propName + ")是必填项");
					}
				}
			}
		}
		return errorList;
	}

	/**
	 * 验证必首尾空格
	 * 
	 * @param dataObj
	 *            数据对象
	 * @param propNames
	 *            属性名
	 * @param displayNames
	 *            显示名
	 * @param errorList
	 *            错误列表
	 * @throws Exception
	 *             异常
	 */
	public static void validateHTBlank(Object dataObj, String[] propNames,
			String[] displayNames, List<String> errorList) throws Exception {
		Assert.isTrue(propNames.length == displayNames.length, "参数名与显示名的个数不一致");
		for (int i = 0; i < propNames.length; i++) {
			String propName = propNames[i];
			String displayName = displayNames[i];
			Object value = ModelUtil.getPropertyValue(dataObj, propName);
			if (null != value && value instanceof String) {
				// 处理全角空格
				String valueStr = value.toString().replace("　", "").trim();
				if (valueStr.length() > 0) {
					if (value.toString().trim().length() != value.toString()
							.length()
							|| value.toString().startsWith("　")
							|| value.toString().endsWith("　")) {
						errorList
								.add(displayName + "(" + propName + ")首尾不能为空格");
					}
				}
			}
		}
	}

	/**
	 * 
	 * Description: 校验用户名
	 * 
	 * @param
	 * @return boolean
	 * @throws
	 * @Author liufei Create Date: 2013-11-16 下午6:39:03
	 */
	public static boolean validateUserName(String userName) {
		return validateByRegex(userName, REG_USERNAME);
	}
	
	/**
	 * 
	 * Description: 校验密码
	 * @param
	 * @return boolean
	 * @throws
	 * @Author liufei
	 * Create Date: 2014年2月23日 上午10:15:23
	 */
	public static boolean validatePassword(String password){
		return validateByRegex(password, REG_PASSWORD);
	}

	/**
	 * 
	 * Description: 校验邮箱
	 * 
	 * @param
	 * @return boolean
	 * @throws
	 * @Author liufei Create Date: 2013-12-13 下午4:34:43
	 */
	public static boolean validateEmail(String email) {
		if (null == email || email.trim().length() == 0) {
			return false;
		}
		return email.matches(REG_EMAIL);
	}

	
	/*================= helpers ==================*/
	/**
	 * 
	 * Description: 按指定正则表达式验证
	 * @param
	 * @return boolean
	 * @throws
	 * @Author liufei
	 * Create Date: 2014年2月23日 上午10:17:05
	 */
	private static boolean validateByRegex(String input, String regex){
		if (null == input || input.trim().length() == 0) {
			return false;
		}
		return input.matches(regex);
	}
	
	/** 
	 * @Description: 校验金额
	 * @param:
	 * @return:
	 * @throws 
	 * @author:	liyufei
	 * @date:	2017年10月31日 下午3:54:48 
	 */
	public static boolean validateAmount(String amount) {
		if (StringUtils.isEmpty(amount)) {
			return false;
		}
		Pattern pattern = Pattern.compile(AMOUNT_STRING);
		Matcher matchFee = pattern.matcher(amount);
		return matchFee.matches();
	}
	
	/** 
	 * @Description: 校验数字
	 * @param:
	 * @return:
	 * @throws 
	 * @author:	liyufei
	 * @date:	2017年11月17日 下午2:32:43 
	 */
	public static boolean validateNumber(String number) {
		if (StringUtils.isEmpty(number)) {
			return false;
		}
		Pattern pattern = Pattern.compile(CHECK_NUMBER);
		Matcher matchNumber = pattern.matcher(number);
		return matchNumber.matches();
	}
}
