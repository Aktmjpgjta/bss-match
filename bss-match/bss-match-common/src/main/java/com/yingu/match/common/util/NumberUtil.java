package com.yingu.match.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NumberUtil {
	public static final boolean NEGATIVE = false; // 是否允许负数表示
	public static final String[][] CAPITAL;
	static {
		CAPITAL = new String[][] {
				{ "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" }, // 0-9
				{ "拾", "佰", "仟" }, // 拾,佰,仟
				{ "万" }, // 万
				{ "亿" }, // 亿
				{ "元" }, // 个位
				{ "角", "分" }, // 小数位,{"角","分","厘"}
				{ "正", "负" } // 正负数的前缀形式,当NEGATIVE=true时生效
		};
	}

	/**
	 * 转换成大写金额
	 * 
	 * @param strMoney(String):规范的数字货币形式字符串
	 */
	public static String toFrmString(String strMoney) {
		StringBuffer sb = new StringBuffer(100);
		String[] tmp = strMoney.trim().split("\\.");

		// --------整数位处理---------------
		char[] ci = tmp[0].toCharArray();
		char theBit = '0'; // 当前数字位
		int zeroBit = 0; // 开始零位
		int bitLen = 0; // 当前位所处的位置(从个位开始0计数)
		boolean flag = false; // 是否具有有效的整数位
		for (int index = 0; index < ci.length; index++) {
			theBit = ci[index]; // 取出当前处理的整数位
			bitLen = ci.length - index - 1; // 计算当前处理的数处于什么位置
			if (zeroBit > 0 && theBit != '0') {
				sb.append(CAPITAL[0][0]);// 加前导零
			}
			if (theBit != '0')
				sb.append(CAPITAL[0][theBit - '0']); // 大写数字
			if (bitLen % 8 == 0) {
				if (bitLen == 0) {
					// 元
					if (ci.length > 1 || theBit != '0')
						sb.append(CAPITAL[4][0]); // 元
				} else {
					// 亿
					sb.append(CAPITAL[3][0]); // 亿
				}
			} else {
				if (bitLen % 4 == 0) {
					// 万
					if (theBit != '0' || zeroBit == 0 || zeroBit - bitLen < 3) {
						sb.append(CAPITAL[2][0]); // 万
					}
				} else {
					// 仟佰拾
					if (theBit != '0')
						sb.append(CAPITAL[1][bitLen % 4 - 1]); // 仟佰拾
				}
			}
			// 检查并条件更新零位
			if (theBit == '0') {
				zeroBit = zeroBit == 0 ? bitLen : zeroBit;
			} else {
				zeroBit = 0;
				flag = true;
			}
		}
		// --------小数位处理---------------
		char[] cf = null;
		if (tmp.length > 1) {
			cf = tmp[1].toCharArray();
			for (int index = 0; index < cf.length; index++) {
				theBit = cf[index]; // 取出当前处理的小数位
				if (zeroBit > 0 && theBit != '0' && flag) {
					sb.append(CAPITAL[0][0]); // 加前导零
				}
				if (theBit != '0')
					sb.append(CAPITAL[0][theBit - '0']); // 大写数字
				if (theBit != '0')
					sb.append(CAPITAL[5][index]); // 角分
				zeroBit = theBit == '0' ? 1 : 0;
			}
		}
		return sb.length() == 0 ? (CAPITAL[0][0] + CAPITAL[4][0]) : sb
				.toString();
	}

	/**
	 * 货币数字形式转换成大写
	 * 
	 * @param strMoney(String):货币的数字形式字符串
	 */
	public static String parseMoney(String strMoney)
			throws NumberFormatException {
		BigDecimal bd = new BigDecimal(strMoney);
		// 输入检查
		if (bd.signum() == -1 && !NEGATIVE)
			throw new NumberFormatException("货币金额不能为负数");
		try {
			bd.setScale(CAPITAL[5].length);
		} catch (ArithmeticException e) {
			throw new NumberFormatException("只能为" + CAPITAL[5].length + "位小数");
		}
		// 大写金额转换
		if (NEGATIVE && bd.signum() != 0) {
			return CAPITAL[6][bd.signum() == -1 ? 1 : 0]
					+ toFrmString(bd.abs().toString());
		} else {
			return toFrmString(bd.toString());
		}
	}
	/**
	 * 
	 * Description: 将数据格式化为需要的位数
	 *
	 * @param 
	 * @return String
	 * @throws 
	 * @Author fei
	 * Create Date: 2013-7-2 上午9:22:51
	 */
	public static String formatInt(int src, int digit){
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(digit);
		return nf.format(src);
	}
	
	/**
	 * 
	 * Description: 将数据格式化为需要的位数
	 *
	 * @param 
	 * @return String
	 * @throws 
	 * @Author fei
	 * Create Date: 2013-7-2 上午9:22:51
	 */
	public static String formatLong(long src, int digit){
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(digit);
		return nf.format(src);
	}
	/*
	public static void main(String[] args) {
		//System.out.println(NumberUtil.formatInt(12, 5));
		String money = null;
		System.out.println("请输入合法的货币金额(^C退出):");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while ((money = br.readLine()) != null) {
			if (money.trim().length() < 1)
				continue;
			try {
				System.out.println("大写:" + parseMoney(money));
			} catch (Exception e) {
				System.out.println("输入格式不合法,错误信息:" + e.getMessage());
			}
		}
		System.out.println("bye");
	}
*/

	/**
	 *  把String类型的对象转换为BigDecimal类型的对象
	 * @param str
	 * @return
	 */
	public static BigDecimal stringToBigDecimal(String str){
		return stringToBigDecimal(str , BigDecimal.ZERO);
	}

	public static BigDecimal stringToBigDecimal(String str, BigDecimal defaultValue){
		if(StringUtils.isEmpty(str)){
			return defaultValue;
		}
		return new BigDecimal(str);
	}

	/**
	 *  把BigDecimal类型的对象转换为String类型的对象
	 * @param number
	 * @return
	 */
	public static String bigDecimalToString(BigDecimal number){
		if(number == null){
			return null;
		}
		if (BigDecimal.ZERO.compareTo(number) == 0) {
			return "0";
		}
		return number.toPlainString();
	}
}
