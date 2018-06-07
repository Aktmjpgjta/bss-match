package com.yingu.match.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yingu.match.common.controller.BaseController;
import com.yingu.match.common.model.Page;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.common.model.Result;
import com.yingu.match.sys.model.Role;
import com.yingu.match.sys.service.ISysService;

@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController{
	
	@Autowired
	private ISysService sysService;
	
	@RequiresPermissions("/sys/role")
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(HttpServletRequest request,Role role) {
		Page<Role> page = getPage(request);
		page = (Page<Role>)sysService.execute(new Parameter(getService(),"findTByPage",page,role)).getResultPage(); //roleService.findTByPage(page,role);
		return getEasyUIData(page);
	}
	
	
	@RequiresPermissions("/sys/role/add")
	@RequestMapping(value="/add")
	@ResponseBody
	public Object add(Role role,Long[] resourcesId) {
		//roleService.insert(role,resourcesId);
		sysService.execute(new Parameter(getService(),"insert",role,resourcesId));
		return Result.successResult();
	}
	
	@RequiresPermissions("/sys/role/delete")
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(Long id) {
		//roleService.delete(id);
		sysService.execute(new Parameter(getService(),"delete",id));
		return Result.successResult();
	}
	
	@RequiresPermissions("/sys/role/update")
	@RequestMapping(value="/update")
	@ResponseBody
	public Object update(Role mrole,Long[] resourcesId) {
		//roleService.update(mrole,resourcesId);
		sysService.execute(new Parameter(getService(),"update",mrole,resourcesId));
		return Result.successResult();
	}
	

	@RequestMapping(value="/listAll")
	@ResponseBody
	public Object listAll() {
		return sysService.execute(new Parameter(getService(),"selectAll")).getResult(); //roleService.selectAll();
	}
	
	@RequestMapping(value="/selectByPrimaryKey")
	@ResponseBody
	public Object selectByPrimaryKey(Long id) {
		return sysService.execute(new Parameter(getService(),"selectByPrimaryKey",id)).getResult(); //roleService.selectByPrimaryKey(id);
	}
	
	@RequiresPermissions(value={"/sys/role/update", "/sys/role/add"}, logical=Logical.OR)
	@RequestMapping(value="/findRoleResourcesByRole")
	@ResponseBody
	public Object findRoleResourcesByRole(Role role) {
		return sysService.execute(new Parameter(getService(),"findRoleResourcesByRole",role)).getResult(); //roleService.findRoleResourcesByRole(role);
	}

	@Override
	public String getBasePath() {
		return "/sys/role";
	}


	@Override
	public String getService() {
		return "roleService";
	}
	
}
