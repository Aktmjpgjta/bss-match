package com.yingu.match.sys.model;

import com.yingu.match.common.model.BaseModel;

public class RoleResources extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2814639517974791520L;

    private Long resourcesId;

    private Long roleId;

	public Long getResourcesId() {
		return resourcesId;
	}

	public void setResourcesId(Long resourcesId) {
		this.resourcesId = resourcesId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}