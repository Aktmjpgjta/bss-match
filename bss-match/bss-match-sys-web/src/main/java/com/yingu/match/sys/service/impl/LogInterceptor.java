package com.yingu.match.sys.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.sys.model.Log;
import com.yingu.match.sys.model.User;
import com.yingu.match.sys.service.ISysService;

public class LogInterceptor implements HandlerInterceptor {

	@Autowired
	private ISysService sysService;
	
	private Date beginTime;// 1、开始时间
	private Date endTime;// 2、结束时间

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		beginTime = new Date();
		return true;
	}

	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		endTime = new Date();
		
		Log log=new Log();
		log.setUrl(request.getRequestURL().toString());
		log.setParameter(JSONObject.toJSONString(request.getParameterMap()));
		log.setRemoteAddr(request.getRemoteAddr());
		log.setAgent(request.getHeader("user-agent"));
		log.setBeginTime(beginTime);
		log.setEndTime(endTime);
		User user=(User) SecurityUtils.getSubject().getPrincipal();
		if(user!=null){
			log.setUserId(user.getId());
		}
		//logService.insert(log);
		sysService.execute(new Parameter("logService","insert",log));
	}

}

