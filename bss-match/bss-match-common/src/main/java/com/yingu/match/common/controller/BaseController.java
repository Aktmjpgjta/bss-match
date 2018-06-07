package com.yingu.match.common.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yingu.match.common.model.Page;
import com.yingu.match.common.util.DateUtils;
import com.yingu.match.common.util.StringUtils;


public abstract class BaseController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 为页面提供基础路径
	 * @return 基础路径
	 */
	public abstract String getBasePath();
	
	/**
	 * 
	 * @Description:服务名称
	 * @author:	daichangfu
	 * @date:	2017年9月26日 下午4:32:50
	 */
	public abstract String getService();
    
	//跳转到主页面
    @RequestMapping(value="")
    public String main(HttpServletRequest request){
    	request.setAttribute("basePath", getBasePath());
		//request.setAttribute("timeStr", System.currentTimeMillis() + new Random().nextInt(100));
    	return getBasePath()+"/main";
    }
	

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
		
		// Timestamp 类型转换
		binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				Date date = DateUtils.parseDate(text);
				setValue(date==null?null:new Timestamp(date.getTime()));
			}
		});
	}
	
	/**
	 * 获取page对象
	 * @param request
	 * @return page对象
	 */
	public <T> Page<T>  getPage(HttpServletRequest request){
		int pageNo=1;	//当前页码
		int pageSize=20;	//每页行数
		
		if(StringUtils.isNotEmpty(request.getParameter("page")))
			pageNo=Integer.valueOf(request.getParameter("page"));
		if(StringUtils.isNotEmpty(request.getParameter("rows")))
			pageSize=Integer.valueOf(request.getParameter("rows"));
		
		return new Page<T> (pageNo, pageSize);
	}
	
	/**
	 * 获取easyui分页数据
	 * @param page
	 * @return map对象
	 */
	public  <T>Map<String, Object> getEasyUIData(Page<T> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", page.getResult());
		map.put("total", page.getTotalCount());
		return map;
	}

}
