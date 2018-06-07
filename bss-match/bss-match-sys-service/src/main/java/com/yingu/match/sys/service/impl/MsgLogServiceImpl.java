package com.yingu.match.sys.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yingu.match.common.service.impl.BaseServiceImpl;
import com.yingu.match.sys.model.MsgLogWithBLOBs;
import com.yingu.match.sys.vo.MsgLogVo;

@Service("msgLogService")
@Transactional
public class MsgLogServiceImpl extends BaseServiceImpl<MsgLogWithBLOBs>{

	/**
	 * 
	 * @Description: 保存业务系统请求报文信息
	 * @param: vo
	 * @author:	daichangfu
	 * @date:	2017年11月25日 下午7:31:38
	 */
	public void insertMsgLog(MsgLogVo vo){
		MsgLogWithBLOBs log = new MsgLogWithBLOBs();
		log.setBizId(vo.getBizId());
		log.setBizSystem(vo.getBizSystem());
		log.setInvokeTimeMillis(vo.getInvokeTimeMillis());
		log.setMseconds(vo.getResTime() - vo.getReqTime());
		log.setProviderName(vo.getProviderName());
		log.setReqmsg(vo.getReqmsg());
		log.setReqTime(vo.getReqTime());
		log.setResTime(vo.getResTime());
		if(vo.getResponseVo()!=null){
			log.setResmsg(JSON.toJSONString(vo.getResponseVo()));
			log.setRetCode(vo.getResponseVo().getRetCode());
			log.setRetInfo(vo.getResponseVo().getRetInfo());
		}
		super.insert(log);
	}
	
}
