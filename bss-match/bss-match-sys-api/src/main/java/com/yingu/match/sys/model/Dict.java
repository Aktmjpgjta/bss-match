package com.yingu.match.sys.model;

import java.util.List;

import com.yingu.match.common.model.BaseModel;

/**
 * 
 * @Description:字典model
 * @author:	daichangfu
 * @date:	2017年7月10日 下午4:04:20
 */
public class Dict extends BaseModel{

    /**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	private String code;

    private String name;

    private String path;

    private Long pid;

    private Integer leaf;
    
    private String value1;

    private String value2;

    private String value3;

    private String value4;

    private String value5;
    
    private String remark;
    
    private Integer status;
    
    private String state;
    
    private List<Dict> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getValue5() {
		return value5;
	}

	public void setValue5(String value5) {
		this.value5 = value5;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Dict> getChildren() {
		return children;
	}

	public void setChildren(List<Dict> children) {
		this.children = children;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState(){
		if(this.state!=null){
			return this.state;
		}
		if(this.leaf==null){
			return null ;
		}
    	if(this.leaf==0){
    		return "closed";
    	}else{
    		return "open";
    	}
    }
}