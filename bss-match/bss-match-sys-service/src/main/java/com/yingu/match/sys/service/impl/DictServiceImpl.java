package com.yingu.match.sys.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yingu.match.common.service.impl.BaseServiceImpl;
import com.yingu.match.common.util.CoreUtil;
import com.yingu.match.common.util.ModelUtil;
import com.yingu.match.sys.dao.DictMapper;
import com.yingu.match.sys.model.Dict;

@Service("dictService")
@Transactional
public class DictServiceImpl extends BaseServiceImpl<Dict> {

	@Autowired
	private DictMapper dictMapper;

	public Dict save(Dict dict, Dict parent, String path){
		dict.setPath(path);
		dict.setLeaf(1);
		ModelUtil.setCommonFields(dict);
		int id = dictMapper.insert(dict);
		if(id>0){
			if(parent.getLeaf()==1){
				parent.setLeaf(0);
				parent.setModifyTime(CoreUtil.generateTimestamp());
				dictMapper.updateByPrimaryKey(parent);
			}
			return dict;
		}
		return null ;
	}
	
	public Dict update(Dict dict, Dict oldDict) throws Exception {
		Date date = CoreUtil.generateTimestamp();
		if(!dict.getCode().equals(oldDict.getCode())){ //需要更新子字典的路径
			List<Dict> list = dictMapper.findDictByPathLike(oldDict.getPath()+"/");
			if(list!=null && list.size()>0){
				for(Dict sub:list){
					String subPath = sub.getPath();
					subPath = subPath.replaceFirst(oldDict.getPath(), dict.getPath());
					sub.setPath(subPath);
					sub.setModifyTime(date);
					dictMapper.updateByPrimaryKey(sub);
				}
			}
		}
		ModelUtil.copyPropertiesIgnoreNull(dict, oldDict, "state");
		oldDict.setModifyTime(date);
		dictMapper.updateByPrimaryKey(oldDict);
		return oldDict;
	}

	public void delete(Long id) {
		Dict dict = dictMapper.selectByPrimaryKey(id);
		dictMapper.deleteByPathLike(dict.getPath());

		Dict dt = new Dict();
		dt.setPid(dict.getPid());
		List<Dict> list = dictMapper.findTByT(dt);
		if(list==null || list.size()==0){
			Dict parent = dictMapper.selectByPrimaryKey(dict.getPid());
			parent.setLeaf(1);
			parent.setModifyTime(CoreUtil.generateTimestamp());
			dictMapper.updateByPrimaryKey(parent);
		}
		
	}

	public Dict findByPath(String path) {
		Dict dict = new Dict();
		dict.setPath(path);
		List<Dict> list = dictMapper.findTByT(dict);
		if(null==list || list.size()<1){
			return null;
		}
		return list.get(0);
	}

	public List<Dict> findSubsByPath(String path) {
		Dict dict = findByPath(path);
		if(dict!=null){
			Dict subDict = new Dict();
			subDict.setPid(dict.getId());
			List<Dict> list = dictMapper.findTByT(subDict);
			return list;
		}
		return null;
	}

	public List<Dict> findSubsByPathStatus(String path, Integer status) {
		Dict dict = findByPath(path);
		if(dict!=null){
			Dict subDict = new Dict();
			subDict.setPid(dict.getId());
			subDict.setStatus(status);
			List<Dict> list = dictMapper.findTByT(subDict);
			return list;
		}
		return null;
	}
	
	public List<Dict> findAllSubsByPath(String path) {
		if(!path.endsWith("/")){
			path += "/";
		}
		List<Dict> list = dictMapper.findDictByPathLike(path);
		return list;
	}

}
