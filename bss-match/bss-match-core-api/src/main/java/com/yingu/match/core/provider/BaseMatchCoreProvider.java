package com.yingu.match.core.provider;

import org.apache.log4j.Logger;

import com.yingu.match.common.vo.ResponseVo;


public interface BaseMatchCoreProvider <T> {

	final static Logger log = Logger.getLogger(BaseMatchCoreProvider.class);
	
	/**
	 * 
	 * @param 处理程序
	 * @return
	 * @throws Exception
	 */
	ResponseVo handle(T vo) ;
	
}
