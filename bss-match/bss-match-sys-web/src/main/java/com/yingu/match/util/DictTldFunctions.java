package com.yingu.match.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yingu.match.common.model.Parameter;
import com.yingu.match.common.spring.SpringContextUtil;
import com.yingu.match.sys.SysConst;
import com.yingu.match.sys.model.Dict;
import com.yingu.match.sys.service.ISysService;


public class DictTldFunctions {

    protected static Logger logger = LoggerFactory.getLogger(DictTldFunctions.class);
	private static ISysService sysService ;
	
	static{
		sysService = SpringContextUtil.getBean("sysService");
	}
	
	/**
	 * 
	 * @Description: 根据字典路径获取子节点
	 * @param: path 字典路径
	 * @return: json
	 * @author:	daichangfu
	 * @date:	2017年10月25日 下午2:02:07
	 */
	public static String findSubsByPath(String path) {
		List<Dict> list = (List<Dict>)sysService.execute(new Parameter("dictService","findSubsByPath",path)).getResult();
		Map<String, String> map = new HashMap<String, String>();
		if(list!=null){
			for(Dict dt : list){
				map.put(dt.getCode(), dt.getName());
			}
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 
	 * @Description: 根据字典路径获取正常状态的子节点
	 * @param: path 字典路径
	 * @return: json
	 * @author:	daichangfu
	 * @date:	2017年10月25日 下午2:02:07
	 */
	public static String findSubsByPathNormal(String path) {
		List<Dict> list = (List<Dict>)sysService.execute(new Parameter("dictService","findSubsByPathStatus",path,SysConst.STATUS_NORMAL)).getResult();
		Map<String, String> map = new HashMap<String, String>();
		if(list!=null){
			for(Dict dt : list){
				map.put(dt.getCode(), dt.getName());
			}
		}
		return JSON.toJSONString(map);
	}
	
}
