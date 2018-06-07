package com.yingu.match.core.provider.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.common.spring.SpringContextUtil;
import com.yingu.match.common.vo.ResponseVo;
import com.yingu.match.core.config.MatchCoreConfig;
import com.yingu.match.core.provider.BaseMatchCoreProvider;
import com.yingu.match.core.provider.IMatchCoreProvider;
import com.yingu.match.core.vo.req.BaseReqVo;
import com.yingu.match.sys.service.ISysService;
import com.yingu.match.sys.vo.MsgLogVo;

/**
 * 
 * @Description: 统一外部调用接口实现
 * @author:	daichangfu
 * @date:	2017年9月19日 上午11:20:45
 */
@Service("matchCoreProvider")
public class MatchCoreProviderImpl implements IMatchCoreProvider<BaseReqVo>{

	private static final Logger logger = LoggerFactory.getLogger(MatchCoreProviderImpl.class);
	
	//@Autowired
	//private ThreadPoolTaskExecutor threadPoolTaskExecutor; //线程池
	
	@Autowired
	private ISysService sysService; //后台服务
	
	@Autowired
	private MatchCoreConfig matchCoreConfig;
	
	
	@Override
	public ResponseVo handle(String interfaceName, BaseReqVo vo) {
		long reqTime = System.currentTimeMillis(); //请求处理开始时间
		ResponseVo res = null ;
		logger.info("request：{}", JSON.toJSONString(vo));
		try {
			BaseMatchCoreProvider<BaseReqVo> provider = SpringContextUtil.getBean(interfaceName);
			if(provider == null){
				res = new ResponseVo(ResponseVo.RET_CODE_FAILED, "接口信息不存在:"+interfaceName);
				logger.error("接口信息不存在:"+interfaceName);
			}else{
				res = provider.handle(vo);
			}
		} catch (Exception e) {
			logger.error("provider调用异常 :" + interfaceName, e);
			res = new ResponseVo(ResponseVo.RET_CODE_FAILED, "系统异常");
			e.printStackTrace();
		}
		logger.info("response：{}", JSON.toJSONString(res));
		long resTime = System.currentTimeMillis(); //请求处理结束时间
		try {
			if(matchCoreConfig.isSaveMsglog()){
				//保存报文请求信息
				sendMsgLog(reqTime, resTime, interfaceName, vo, res);
			}
		} catch (Exception e) {
			logger.error("保存报文请求信息异常", e);
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 
	 * @Description: 保存请求报文信息
	 * @param: ...
	 * @author:	daichangfu
	 * @date:	2017年11月25日 下午7:49:51
	 */
	public void sendMsgLog(final long reqTime, final long resTime,
			final String providerName, final BaseReqVo reqVo,
			final ResponseVo resVo) {
	//	threadPoolTaskExecutor.execute(new Thread(){
	//		@Override
	//		public void run() {
				MsgLogVo vo = new MsgLogVo();
				if(reqVo.getBizId()==null || "".equals(reqVo.getBizId().trim())){
					vo.setBizId(reqVo.getBatchId());
				}else{
					vo.setBizId(reqVo.getBizId());
				}
				vo.setProviderName(providerName);
				vo.setReqTime(reqTime);
				vo.setResTime(resTime);
				vo.setBizSystem(reqVo.getBizSystem());
				vo.setInvokeTimeMillis(reqVo.getInvokeTimeMillis());
				vo.setReqmsg(JSON.toJSONString(reqVo));
				vo.setResponseVo(resVo);
				sysService.execute(new Parameter("msgLogService", "insertMsgLog", vo));
	//		}
	//	});
	}
	
}
