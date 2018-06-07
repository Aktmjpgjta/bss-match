package com.yingu.match.core.vo.req;

import java.io.Serializable;

public class BaseReqVo implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String batchId; //批次号
	
	private String bizId; //业务流水号
	
	private String bizSystem;//业务系统
	
	private String subSystem;//业务子系统

	private Long invokeTimeMillis;	//请求时间戳
	
	public String getBizSystem() {
		return bizSystem;
	}
	public void setBizSystem(String bizSystem) {
		this.bizSystem = bizSystem;
	}
	
	public String getSubSystem() {
		return subSystem;
	}
	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public Long getInvokeTimeMillis() {
		return invokeTimeMillis;
	}

	public void setInvokeTimeMillis(Long invokeTimeMillis) {
		this.invokeTimeMillis = invokeTimeMillis;
	}
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
}
