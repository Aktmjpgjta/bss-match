package com.yingu.match.sys.model;

import com.yingu.match.common.model.BaseModel;

public class Role extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3582588209589180635L;

    private String name;

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}