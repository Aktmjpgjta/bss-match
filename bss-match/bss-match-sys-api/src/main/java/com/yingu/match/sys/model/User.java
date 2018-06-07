package com.yingu.match.sys.model;

import com.yingu.match.common.model.BaseModel;

public class User extends BaseModel{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7484136779753770396L;

    private String username;

    private String password;
    
    private Integer status;	//状态
    
    private String realname; //真实姓名
    
    private String email; //邮箱

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", username=" + username + ", password=" + password + "]";
	}


}