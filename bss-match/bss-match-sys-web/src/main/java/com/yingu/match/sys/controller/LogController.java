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
import com.yingu.match.sys.model.Log;
import com.yingu.match.sys.service.ISysService;

@Controller
@RequestMapping("/sys/log")
public class LogController extends BaseController{
	
	@Autowired
	private ISysService sysService;
	
	@RequiresPermissions("/sys/log")
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(HttpServletRequest request,Log log) {
		Page<Log> page = getPage(request);
		page = (Page<Log>)sysService.execute(new Parameter(getService(), "findTByPage", page, log)).getResultPage(); //logService.findTByPage(page,log);
		return getEasyUIData(page);
	}

	@RequiresPermissions("/sys/log/delete")
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(String ids) {
		//logService.deleteByPrimaryKey(id);
		Long t1 = System.currentTimeMillis();
		sysService.execute(new Parameter(getService(), "deleteByPrimaryKey", ids));
		Long t2 = System.currentTimeMillis();
		System.out.println("耗时 ************ : "+(t2-t1));
		return Result.successResult();
	}

	@Override
	public String getBasePath() {
		return "/sys/log";
	}

	@Override
	public String getService() {
		return "logService";
	}
}