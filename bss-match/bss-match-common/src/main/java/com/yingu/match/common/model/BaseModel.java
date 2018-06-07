package com.yingu.match.common.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @Description: 基础模型
 * @author:	xianzhiqiang
 * @date:	2017年6月19日 下午3:01:42
 */
public class BaseModel implements Serializable {

	private static final long serialVersionUID = -4300900555741461835L;

	private Long id;//主键ID
	private Date createTime;//创建时间
	private Date modifyTime;//修改时间
	private Long sortidx;//排序号
	private Integer version = 0;//版本(用于乐观锁控制)
	private String bizSystem;//业务系统
	private String subSystem;//业务子系统
	private Long invokeTimeMillis;	//请求时间戳

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public Long getSortidx() {
		return sortidx;
	}
	public void setSortidx(Long sortidx) {
		this.sortidx = sortidx;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}


	public Long getInvokeTimeMillis() {
		return invokeTimeMillis;
	}

	public void setInvokeTimeMillis(Long invokeTimeMillis) {
		this.invokeTimeMillis = invokeTimeMillis;
	}
}
