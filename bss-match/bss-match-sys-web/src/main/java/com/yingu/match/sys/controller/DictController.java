package com.yingu.match.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yingu.match.common.controller.BaseController;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.common.model.Result;
import com.yingu.match.sys.SysConst;
import com.yingu.match.sys.model.Dict;
import com.yingu.match.sys.service.ISysService;

@Controller
@RequestMapping("/sys/dict")
public class DictController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(DictController.class);
	
	@Autowired
	private ISysService sysService;
	
	@Override
	public String getBasePath() {
		return "/sys/dict";
	}
	
	@RequestMapping(value="")
	public String main(HttpServletRequest request) {
		String path = request.getParameter("path");
		request.setAttribute("path", path);
		logger.info("path:"+path);
		return super.main(request);
	}
	
	@RequiresPermissions("/sys/dict/list")
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(HttpServletRequest request,Dict dict) {
		List<Dict> list = null;
		try {
			Parameter parameter = new Parameter(getService(),"findTByT",dict);
			if(dict!=null && dict.getId()!=null){
				dict.setPath(null);
				dict.setPid(dict.getId());
				list = (List<Dict>)sysService.execute(parameter).getResultList();//dictsysService.findTByT(dict);
			}else{
				list = (List<Dict>)sysService.execute(parameter).getResultList();
				for(Dict d:list){
					Dict subDict = new Dict();
					subDict.setPid(d.getId());
					parameter.setParam(subDict);
					List<Dict> subList = (List<Dict>)sysService.execute(parameter).getResultList();
					if(subList!=null && subList.size()>0){
						d.setChildren(subList);
						d.setState("open");
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取字典列表异常", e);
			e.printStackTrace();
		}
		return list;
	}
	
	@RequiresPermissions("/sys/dict/add")
	@RequestMapping(value="/add")
	@ResponseBody
	public Object add(Dict dict) {
		Result result = Result.successResult();
		Dict pDict = (Dict)sysService.execute(new Parameter(getService(),"selectByPrimaryKey",dict.getPid())).getResult();//dictsysService.selectByPrimaryKey(dict.getPid());
		if(pDict==null){
			result = new Result(Result.ERROR, "父节点不存在", null);
		}else{
			String parentPath = pDict.getPath().trim();
			if(!parentPath.endsWith("/")){
				parentPath = parentPath+"/";
			}
			String path = parentPath+dict.getCode();
			Dict queryDict = new Dict();
			queryDict.setPath(path);
			List<Dict> list = (List<Dict>)sysService.execute(new Parameter(getService(),"findTByT",queryDict)).getResult(); //dictsysService.findTByT(queryDict);
			if(list!=null && list.size()>0){
				result = new Result(Result.ERROR, "相同路径的字典已存在", null);
			}else{
				Dict resultDict = (Dict)sysService.execute(new Parameter(getService(),"save",dict,pDict,path)).getResult();//dictsysService.save(dict, pDict, path);
				result.setObj(resultDict);
			}
		}
		return result;
	}
	
	@RequiresPermissions("/sys/dict/update")
	@RequestMapping(value="/update")
	@ResponseBody
	public Object update(Dict dict) {
		Result result = Result.successResult();
		try {
			Dict oldDict = (Dict)sysService.execute(new Parameter(getService(),"selectByPrimaryKey",dict.getId())).getResult();//dictsysService.selectByPrimaryKey(dict.getId());
			if("/".equals(oldDict.getPath())){
				result = new Result(Result.ERROR, "根节点不可被修改", null);
				return result;
			}
			if(!dict.getCode().equals(oldDict.getCode())){	//关键字变更，说明路径有变化
				Dict pDict = (Dict)sysService.execute(new Parameter(getService(),"selectByPrimaryKey",dict.getPid())).getResult();//dictsysService.selectByPrimaryKey(dict.getPid());
				String parentPath = pDict.getPath().trim();
				if(!parentPath.endsWith("/")){
					parentPath = parentPath+"/";
				}
				String path = parentPath+dict.getCode();
				Dict queryDict = new Dict();
				queryDict.setPath(path);
				List<Dict> list = (List<Dict>)sysService.execute(new Parameter(getService(),"findTByT",queryDict)).getResult(); //dictsysService.findTByT(queryDict);
				if(list!=null && list.size()>0 && list.get(0).getId()!=dict.getId()){
					result = new Result(Result.ERROR, "相同路径的字典已存在", null);
				}else{
					dict.setPath(path);
					dict = (Dict)sysService.execute(new Parameter(getService(),"update",dict,oldDict)).getResult(); //dictsysService.update(dict, oldDict);
					result.setObj(dict);
				}
			}else{
				dict = (Dict)sysService.execute(new Parameter(getService(),"update",dict,oldDict)).getResult(); //dictsysService.update(dict, oldDict);
				result.setObj(dict);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("字典更新异常",e);
			result = new Result(Result.ERROR, e.toString(), null);
		}
		return result;
	}
	
	@RequiresPermissions("/sys/dict/delete")
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(Long id) {
	//	dictsysService.delete(id);
		sysService.execute(new Parameter(getService(),"delete",id)); 
		return Result.successResult();
	}
	
	@RequestMapping(value="/findSubsByPathNormal")
	@ResponseBody
	public Object findSubsByPathNormal(String path) {
		return sysService.execute(new Parameter(getService(),"findSubsByPathStatus",path,SysConst.STATUS_NORMAL)).getResult(); //dictsysService.findSubsByPathStatus(path, SysConst.STATUS_NORMAL);
	}
	@RequestMapping(value="/findSubsByPath")
	@ResponseBody
	public Object findSubsByPath(String path) {
		return sysService.execute(new Parameter(getService(),"findSubsByPath",path)).getResult(); //dictsysService.findSubsByPath(path);
	}
	
	/**
	 * 
	 * @Description: 通过路径获取字典
	 * @author:	xianzhiqiang
	 * @date:	2017年7月24日 上午11:39:20
	 */
	@RequestMapping(value="/getDict")
	@ResponseBody
	public Dict getDict(String path){
//		String path = request.getParameter("path");
		Dict dict = (Dict)sysService.execute(new Parameter(getService(),"findByPath",path)).getResult();  //dictsysService.findByPath(path);
		return dict;
		
	}

	@Override
	public String getService() {
		return "dictService";
	}
}
