package com.yingu.match.sys.model;

import com.yingu.match.common.model.BaseModel;

public class Resources extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4125580367922291000L;

    private Long pid;

    private String name;

    private String url;
    
    private String type;
    
    private Integer sort;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		 this.type = type == null ? null : type.trim();
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		 this.sort = sort;	
	}
}