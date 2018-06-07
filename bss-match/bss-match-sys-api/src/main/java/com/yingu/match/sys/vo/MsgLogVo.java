package com.yingu.match.sys.vo;

import java.io.Serializable;

import com.yingu.match.common.vo.ResponseVo;


public class MsgLogVo implements Serializable {

    /**
	 * UID
	 */
	private static final long serialVersionUID = 2713622485211662675L;

	private String bizId; //交易流水号

    private String providerName; //接口名称

    private Long reqTime;	//接收请求时间

    private Long resTime;	//处理完成时间
    
	private String bizSystem;//业务系统
	
	private Long invokeTimeMillis;	//请求时间戳
	
    private String reqmsg; //请求参数
    
    private ResponseVo responseVo;

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Long getReqTime() {
		return reqTime;
	}

	public void setReqTime(Long reqTime) {
		this.reqTime = reqTime;
	}

	public Long getResTime() {
		return resTime;
	}

	public void setResTime(Long resTime) {
		this.resTime = resTime;
	}

	public ResponseVo getResponseVo() {
		return responseVo;
	}

	public void setResponseVo(ResponseVo responseVo) {
		this.responseVo = responseVo;
	}

	public String getBizSystem() {
		return bizSystem;
	}

	public void setBizSystem(String bizSystem) {
		this.bizSystem = bizSystem;
	}

	public Long getInvokeTimeMillis() {
		return invokeTimeMillis;
	}

	public void setInvokeTimeMillis(Long invokeTimeMillis) {
		this.invokeTimeMillis = invokeTimeMillis;
	}

	public String getReqmsg() {
		return reqmsg;
	}

	public void setReqmsg(String reqmsg) {
		this.reqmsg = reqmsg;
	}
	
}
