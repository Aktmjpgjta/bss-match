package com.yingu.match.sys.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yingu.match.common.controller.BaseController;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.common.model.Result;
import com.yingu.match.common.util.UserUtil;
import com.yingu.match.sys.model.Resources;
import com.yingu.match.sys.model.User;
import com.yingu.match.sys.service.ISysService;

@Controller
@RequestMapping("/sys/resources")
public class ResourcesController extends BaseController {

	@Autowired
	private ISysService sysService;
	
	@RequiresPermissions("/sys/resources/add")
	@RequestMapping(value="/add")
	@ResponseBody
	public Object add(Resources resources) {
		//resourcesService.insert(resources);
		resources=(Resources)sysService.execute(new Parameter(getService(),"save",resources)).getResult();
		Result result = Result.successResult();
		result.setObj(resources);
		return result;
	}
	
	@RequiresPermissions("/sys/resources/delete")
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(Long id) {
		//resourcesService.delete(id);
		sysService.execute(new Parameter(getService(),"delete", id));
		return Result.successResult();
	}
	
	@RequiresPermissions("/sys/resources/update")
	@RequestMapping(value="/update")
	@ResponseBody
	public Object update(Resources mresources) {
		//resourcesService.updateByPrimaryKey(mresources);
		mresources=(Resources)sysService.execute(new Parameter(getService(),"update", mresources)).getResult();
		Result result = Result.successResult();
		result.setObj(mresources);
		return result;
	}
	
	@RequiresPermissions(value={"/sys/role/update", "/sys/role/add", "/sys/resources"}, logical=Logical.OR)
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list() {
		return sysService.execute(new Parameter(getService(),"findResources")).getResultList();//resourcesService.findResources();
	}
	

	@RequestMapping(value="/findResourcesEMUByResources")
	@ResponseBody
	public Object findResourcesEMUByResources() {
		return sysService.execute(new Parameter(getService(),"findResourcesEMUByResources",(User)UserUtil.getUser())).getResultList(); //resourcesService.findResourcesEMUByResources((User)UserUtil.getUser());
	}
	
	@RequiresPermissions("/sys/resources/update")
	@RequestMapping(value="/selectByPrimaryKey")
	@ResponseBody
	public Object selectByPrimaryKey(Long id) {
		return sysService.execute(new Parameter(getService(),"selectByPrimaryKey",id)).getResult(); //resourcesService.selectByPrimaryKey(id);
	}

	@Override
	public String getBasePath() {
		return "/sys/resources";
	}

	@Override
	public String getService() {
		return "resourcesService";
	}
	
}
