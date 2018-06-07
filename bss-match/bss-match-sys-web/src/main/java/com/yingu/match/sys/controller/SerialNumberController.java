package com.yingu.match.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yingu.match.common.controller.BaseController;
import com.yingu.match.common.model.Page;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.common.model.Result;
import com.yingu.match.sys.model.SerialNumber;
import com.yingu.match.sys.service.ISysService;

/**
 * 
 * @Description:流水号管理
 * @author:	daichangfu
 * @date:	2017年11月24日 下午4:30:20
 */
@Controller
@RequestMapping("/sys/serialNumber")
public class SerialNumberController extends BaseController{

	@Autowired
	private ISysService sysService;
	
	@RequiresPermissions("/sys/serialNumber")
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(HttpServletRequest request,SerialNumber serialNumber) {
		Page<?> page = getPage(request);
		page = sysService.execute(new Parameter(getService(), "findTByPage", page, serialNumber)).getResultPage();
		return getEasyUIData(page);
	}
	
	@RequiresPermissions("/sys/serialNumber/add")
	@RequestMapping(value="/add")
	@ResponseBody
	public Object add(SerialNumber serialNumber) {
		Result result = Result.successResult();
		int r = (Integer)sysService.execute(new Parameter(getService(), "insert", serialNumber)).getResult(); //userService.insert(user,roleId);
		if(r == 0){
			result = new Result(Result.ERROR, "关键字已存在！", null);
		}
		return result;
	}
	
	@RequiresPermissions("/sys/serialNumber/update")
	@RequestMapping(value="/update")
	@ResponseBody
	public Object update(SerialNumber serialNumber) {
		sysService.execute(new Parameter(getService(), "update", serialNumber));
		return Result.successResult();
	}
	
	@RequiresPermissions("/sys/serialNumber/delete")
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(String id) {
		sysService.execute(new Parameter(getService(), "deleteByPrimaryKey", id));
		return Result.successResult();
	}
	
	@Override
	public String getBasePath() {
		return "/sys/serialNumber";
	}

	@Override
	public String getService() {
		return "serialNumberService";
	}

}
