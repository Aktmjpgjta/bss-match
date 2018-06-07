package com.yingu.match.sys.model;

import com.yingu.match.common.model.BaseModel;

public class MsgLog  extends BaseModel {

    /**
	 * UID
	 */
	private static final long serialVersionUID = 2092556580394248979L;

	private String bizId;

    private Long mseconds;

    private String providerName;

    private Long reqTime;

    private Long resTime;

    private String retCode;

    private String retInfo;


    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId == null ? null : bizId.trim();
    }

    public Long getMseconds() {
        return mseconds;
    }

    public void setMseconds(Long mseconds) {
        this.mseconds = mseconds;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName == null ? null : providerName.trim();
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

	public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode == null ? null : retCode.trim();
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo == null ? null : retInfo.trim();
    }
}