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
import com.yingu.match.sys.model.MsgLogWithBLOBs;
import com.yingu.match.sys.service.ISysService;

@Controller
@RequestMapping("/sys/msgLog")
public class MsgLogController extends BaseController {

	@Autowired
	private ISysService sysService;
	
	@RequiresPermissions("/sys/msgLog")
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(HttpServletRequest request,MsgLogWithBLOBs msgLog) {
		Page<?> page = getPage(request);
		page = sysService.execute(new Parameter(getService(), "findTByPage", page, msgLog)).getResultPage();
		return getEasyUIData(page);
	}
	
	@RequestMapping(value="/viewParams")
	@ResponseBody
	public Object viewParams(Long id){
		Object obj = sysService.execute(new Parameter(getService(), "selectByPrimaryKey", id)).getResult();
		return obj ;
	}
	
	@Override
	public String getBasePath() {
		return "/sys/msgLog";
	}

	@Override
	public String getService() {
		return "msgLogService";
	}

}
