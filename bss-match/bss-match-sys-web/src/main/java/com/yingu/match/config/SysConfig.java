package com.yingu.match.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("sysConfig")
public class SysConfig {
	
	@Value("${iscode}")
	private boolean iscode; //是否进行验证码校验

	public boolean isIscode() {
		return iscode;
	}

	public void setIscode(boolean iscode) {
		this.iscode = iscode;
	}
	
}
