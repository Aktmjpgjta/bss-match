package com.yingu.match.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.common.service.BaseServiceProvider;
import com.yingu.match.common.spring.SpringContextUtil;
import com.yingu.match.common.util.InstanceUtil;

public class BaseServiceProviderImpl implements BaseServiceProvider {

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceProviderImpl.class);
	
	public Parameter execute(Parameter parameter) {
		logger.debug("request：{}", JSON.toJSONString(parameter));
		Object service = SpringContextUtil.getBean(parameter.getService());
		try {
			String method = parameter.getMethod();
			Object[] param = parameter.getParam();
			Object result = InstanceUtil.invokeMethod(service, method, param);
			Parameter response = new Parameter(result);
			logger.debug("response：{}", JSON.toJSONString(response));
			return response;
		} catch (Exception e) {
        	e.printStackTrace();
            logger.error("Service execute error :",e);
            throw e;
		}
	}

}
