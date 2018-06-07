/**
 *  Copyright (c) 2012-2014 http://www.eryansky.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yingu.match.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author hc
 *
 */
@SuppressWarnings("serial")
public class Result implements Serializable {

	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;
	/**
	 * 警告
	 */
	public static final int WARN = 2;
	/**
	 * 部分成功
	 */
	public static final int PART = 3;
	/**
	 * 失败
	 */
	public static final int ERROR = 0;
	
	/**
	 * 成功消息
	 */
	public static final String SUCCESS_MSG = "操作成功！";
	/**
	 * 失败消息
	 */
	public static final String ERROR_MSG = "操作失败:发生未知异常！";

	/**
	 * 结果状态码(可自定义结果状态码) 1:操作成功 0:操作失败
	 */
	private int code;
	/**
	 * 响应结果描述
	 */
	private String msg;
	/**
	 * 成功id
	 */
	private List<String> successIds;
	/**
	 * 成功数量
	 */
	private Integer successAmount;
	/**
	 * 失败Id
	 */
	private List<String> failIds;
	/**
	 * 失败数量
	 */
	private Integer failAmount;
	/**
	 * 总数量
	 */
	private Integer amount;
	/**
	 * 其它数据信息（比如跳转地址）
	 */
	private Object obj;

	public Result() {
		super();
	}

	/**
	 * 
	 * @param code
	 *            结果状态码(可自定义结果状态码或者使用内部静态变量 1:操作成功 0:操作失败 2:警告)
	 * @param msg
	 *            响应结果描述
	 * @param obj
	 *            其它数据信息（比如跳转地址）
	 */
	public Result(int code, String msg, Object obj) {
		super();
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}

	/**
	 * 默认操作成功结果.
	 */
	public static Result successResult() {
		return new Result(SUCCESS, SUCCESS_MSG, null);
	}

	/**
	 * 默认操作失败结果.
	 */
	public static Result errorResult() {
		return new Result(ERROR, ERROR_MSG, null);
	}

	/**
	 * 结果状态码(可自定义结果状态码) 1:操作成功 0:操作失败
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 设置结果状态码(约定 1:操作成功 0:操作失败 2:警告)
	 * 
	 * @param code
	 *            结果状态码
	 */
	public Result setCode(int code) {
		this.code = code;
        return this;
	}

	/**
	 * 响应结果描述
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置响应结果描述
	 * 
	 * @param msg
	 *            响应结果描述
	 */
	public Result setMsg(String msg) {
		this.msg = msg;
        return this;
	}

	/**
	 * 其它数据信息（比如跳转地址）
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * 设置其它数据信息（比如跳转地址）
	 * 
	 * @param obj
	 *            其它数据信息（比如跳转地址）
	 */
	public Result setObj(Object obj) {
		this.obj = obj;
        return this;
	}

	public List<String> getSuccessIds() {
		return successIds;
	}

	public void setSuccessIds(List<String> successIds) {
		this.successIds = successIds;
	}

	public List<String> getFailIds() {
		return failIds;
	}

	public void setFailIds(List<String> failIds) {
		this.failIds = failIds;
	}

	public Integer getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(Integer successAmount) {
		this.successAmount = successAmount;
	}

	public Integer getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(Integer failAmount) {
		this.failAmount = failAmount;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
