package com.yingu.match.core.provider;

import com.yingu.match.common.vo.ResponseVo;

/**
 * 
 * @Description: 统一外部调用接口
 * @author:	daichangfu
 * @date:	2017年9月19日 上午10:55:40 
 * @param <T>
 */
public interface IMatchCoreProvider <T extends com.yingu.match.core.vo.req.BaseReqVo> {

	/**
	 * 
	 * @param 处理程序
	 * @return
	 * @throws Exception
	 */
	ResponseVo handle(String interfaceName, T vo) ;
	
}
