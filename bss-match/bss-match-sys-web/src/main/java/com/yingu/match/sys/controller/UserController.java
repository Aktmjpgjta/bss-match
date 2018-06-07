package com.yingu.match.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yingu.match.common.controller.BaseController;
import com.yingu.match.common.model.Page;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.common.model.Result;
import com.yingu.match.sys.SysConst;
import com.yingu.match.sys.model.User;
import com.yingu.match.sys.service.ISysService;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController{
	
	@Autowired
	private ISysService sysService;
	
	@RequiresPermissions("/sys/user")
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(HttpServletRequest request,User user) {
		Page<User> page = getPage(request);
		page = (Page<User>)sysService.execute(new Parameter(getService(), "findTByPage", page, user)).getResultPage(); //userService.findTByPage(page,user);
		return getEasyUIData(page);
	}
	
	@RequiresPermissions("/sys/user/add")
	@RequestMapping(value="/add")
	@ResponseBody
	public Object add(User user,Long[] roleId) {
		Result result = Result.successResult();
		int r = (Integer)sysService.execute(new Parameter(getService(), "insert", user, roleId)).getResult(); //userService.insert(user,roleId);
		if(r == 0){
			result = new Result(Result.ERROR, "用户名已存在！", null);
		}
		return result;
	}
	
	@RequiresPermissions("/sys/user/update")
	@RequestMapping(value="/selectByPrimaryKey")
	@ResponseBody
	public Object selectByPrimaryKey(Long id) {
		User muser = (User)sysService.execute(new Parameter(getService(), "selectByKey", id)).getResult(); //userService.selectByKey(id);
		return muser;
	}
	
	@RequiresPermissions(value={"/sys/user/update","/sys/user/add"}, logical=Logical.OR)
	@RequestMapping(value="/findUserRoleByUser")
	@ResponseBody
	public Object findUserRoleByUser(User user) {
		return sysService.execute(new Parameter(getService(), "findUserRoleByUser", user)).getResult(); //userService.findUserRoleByUser(user);
	}
	
	@RequiresPermissions("/sys/user/update")
	@RequestMapping(value="/update")
	@ResponseBody
	public Object update(User muser,Long[] roleId) {
		muser.setPassword(null);
		//userService.update(muser,roleId);
		sysService.execute(new Parameter(getService(), "update", muser, roleId));
		return Result.successResult();
	}
	
	/**
	 * 
	 * @Description: 重置密码
	 * @param: user
	 * @return: Object
	 * @author:	daichangfu
	 * @date:	2017年8月4日 下午2:10:18
	 */
	@RequiresPermissions("/sys/user/resetPwd")
	@RequestMapping(value="/resetPwd")
	@ResponseBody
	public Object resetPwd(User user){
		User u = (User)sysService.execute(new Parameter(getService(), "selectByKey", user.getId())).getResult(); //userService.selectByKey(user.getId());
		u.setPassword(user.getPassword());
		//userService.resetPwd(u);
		sysService.execute(new Parameter(getService(), "resetPwd", u));
		return Result.successResult();
	}
	
	@RequiresPermissions("/sys/user/delete")
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(Long id) {
		//userService.delete(id);
		sysService.execute(new Parameter(getService(), "delete", id));
		return Result.successResult();
	}
	
	/*@RequestMapping(value="/findUserByUser")
	@ResponseBody
	public Object findUserByUser(User user) {
		return sysService.execute(new Parameter(getService(), "findTByTOne", user)).getResult(); //userService.findTByTOne(user);
	}*/

	@RequiresPermissions("/sys/user/disableUser")
	@RequestMapping(value="/disableUser")
	@ResponseBody
	public Object disableUser(User user){
		User u = (User)sysService.execute(new Parameter(getService(), "selectByKey", user.getId())).getResult(); //userService.selectByKey(user.getId());
		if(SysConst.STATUS_NORMAL.equals(u.getStatus())){
			u.setStatus(SysConst.STATUS_DELETE);
		}else{
			u.setStatus(SysConst.STATUS_NORMAL);
		}
		//userService.updateByPrimaryKey(u);
		sysService.execute(new Parameter(getService(), "updateByPrimaryKey", u));
		return Result.successResult();
	}

	@RequestMapping(value="/updatePwd")
	@ResponseBody
	public Object updatePwd(String oldPassword, String password){
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		oldPassword = DigestUtils.md5Hex(oldPassword);
		if(user.getPassword().equals(oldPassword)){
			User u = (User)sysService.execute(new Parameter(getService(), "selectByKey", user.getId())).getResult();
			u.setPassword(DigestUtils.md5Hex(password));
			sysService.execute(new Parameter(getService(), "updateByPrimaryKey", u));
		}else{
			return new Result(2,"旧密码不正确",null);
		}
		return Result.successResult();
	}
	
	@Override
	public String getBasePath() {
		return "/sys/user";
	}


	@Override
	public String getService() {
		// TODO Auto-generated method stub
		return "userService";
	}
}
