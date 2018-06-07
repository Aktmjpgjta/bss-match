package com.yingu.match.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("matchCoreConfig")
public class MatchCoreConfig {

	@Value("${is_save_msglog}")
	private boolean isSaveMsglog; //是否保存消息日志

	public boolean isSaveMsglog() {
		return isSaveMsglog;
	}

	public void setSaveMsglog(boolean isSaveMsglog) {
		this.isSaveMsglog = isSaveMsglog;
	}

	
}

