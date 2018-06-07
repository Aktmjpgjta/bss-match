package com.yingu.match.sys.model;

import com.yingu.match.common.model.BaseModel;

public class UserRole extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2146055323640680310L;

    private Long userId;

    private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


}