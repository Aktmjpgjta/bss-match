package com.yingu.match.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yingu.match.common.controller.BaseController;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.common.spring.SpringContextUtil;
import com.yingu.match.common.util.StringUtils;
import com.yingu.match.config.SysConfig;
import com.yingu.match.sys.model.Dict;
import com.yingu.match.sys.model.User;
import com.yingu.match.sys.service.ISysService;
import com.yingu.match.util.ValidateCode;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private ISysService sysService;
	
	@RequestMapping(value = "/")
    public String index(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		if(subject != null){
			User user = (User)subject.getPrincipal();
			request.setAttribute("realname", user.getRealname());
		}
        return "index";
    }
  
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/";
        }
        return "superlogin";
    }

  
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object loginPost(HttpServletRequest request,String username, String password) {
    	Subject user = SecurityUtils.getSubject();
    	UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password).toCharArray());
    	Map<String, Object>	map=new HashMap<String, Object>();
    	SysConfig sysConfig = (SysConfig)SpringContextUtil.getBean("sysConfig");
    	if(sysConfig.isIscode()){
    		String code = request.getParameter("code");  
    		HttpSession session = request.getSession();  
    		String sessionCode = (String) session.getAttribute("code");  
    		if (!StringUtils.equalsIgnoreCase(code, sessionCode)) {  //忽略验证码大小写 
    			map.put("msg", "code");
    			return map;
    		}  
    	}
        try {
            user.login(token);
        } catch (UnknownAccountException e) {
        //	e.printStackTrace();
        	map.put("msg", "账号不存在");
        } catch (LockedAccountException e) {
        	map.put("msg", "账号被锁定");
		} catch (DisabledAccountException e) {
        //	e.printStackTrace();
        	map.put("msg", "账号未启用");
        } catch (IncorrectCredentialsException e) {
        //	e.printStackTrace();
        	map.put("msg", "密码错误");
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	map.put("msg", "未知错误,请联系管理员");
        }
        return map;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
    	Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "superlogin";
    }


	@Override
	public String getBasePath() {
		return null;
	}

	@Override
	public String getService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** 
	 * 响应验证码页面 
	 * @return 
	 */  
	@RequestMapping(value="/validateCode")  
	@ResponseBody
	public String validateCode(HttpServletRequest request,HttpServletResponse response) throws Exception{  
	    // 设置响应的类型格式为图片格式  
	    response.setContentType("image/jpeg");  
	    //禁止图像缓存。  
	    response.setHeader("Pragma", "no-cache");  
	    response.setHeader("Cache-Control", "no-cache");  
	    response.setDateHeader("Expires", 0);  
	  
	    HttpSession session = request.getSession();  
	  
	    ValidateCode vCode = new ValidateCode(100,35,5,100);  
	    session.setAttribute("code", vCode.getCode());  
	    vCode.write(response.getOutputStream());  
	    return null;  
	}  
}
