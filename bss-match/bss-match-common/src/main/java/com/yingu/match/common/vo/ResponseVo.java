package com.yingu.match.common.vo;

import java.io.Serializable;


public class ResponseVo implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final String RET_CODE_SUCCESS = "0000";//成功
	public static final String RET_CODE_SEND_FAILED = "1000";//发送或接收失败
	public static final String RET_CODE_TEST_FAILED = "1999";//测试失败
	public static final String RET_CODE_PROCESSING = "2002";//处理中
	public static final String RET_CODE_FAILED = "3000";//失败
	public static final String RET_CODE_REPEAT = "3005";// 流水号重复
	public static final String RET_CODE_PART_SUCCESS = "4000";//部分成功
	public static final String RET_CODE_UNDONE = "5000";//已撤销
	public static final String RET_CODE_VALIDATE_FAILED = "3027";//参数校验失败
	
	public static final String HRBB_SUCCESS = "S"; //哈尔滨银行接口调用成功
	public static final String HRBB_FAILED = "E"; //哈尔滨银行接口调用失败
	public static final String HRBB_WARNING = "W"; //哈尔滨银行接口调用警告（视作成功）
	public static final String HRBB_DATA_INCONSISTENCY = "R"; //哈尔滨银行接口调用数据不一致
	
	/**
	 * 响应码
	 */
	private String retCode;
	
	/**
	 * 响应信息
	 */
	private String retInfo;
	
	/**
	 * 其它数据信息
	 */
	private Object obj;
	
	public ResponseVo(){}

	public ResponseVo(String retCode, String retInfo) {
		this.retCode = retCode;
		this.retInfo = retInfo;
	}
	
	public ResponseVo(String retCode, String retInfo, Object obj) {
		this.retCode = retCode;
		this.retInfo = retInfo;
		this.obj = obj;
	}
	
	/**
	 * 默认操作成功结果.
	 */
	public static ResponseVo successRes() {
		return new ResponseVo(RET_CODE_SUCCESS, "处理成功");
	}

	/**
	 * 默认操作失败结果.
	 */
	public static ResponseVo errorRes() {
		return new ResponseVo(RET_CODE_FAILED, "处理失败");
	}
	
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetInfo() {
		return retInfo;
	}

	public void setRetInfo(String retInfo) {
		this.retInfo = retInfo;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
