package com.yingu.match.sys.service.impl;

import com.yingu.match.common.model.Attributes;
import com.yingu.match.common.model.JsonTreeData;
import com.yingu.match.common.service.impl.BaseServiceImpl;
import com.yingu.match.common.util.TreeNodeUtil;
import com.yingu.match.sys.dao.ResourcesMapper;
import com.yingu.match.sys.dao.RoleResourcesMapper;
import com.yingu.match.sys.model.Resources;
import com.yingu.match.sys.model.RoleResources;
import com.yingu.match.sys.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@Service("resourcesService")
@Transactional
public class ResourcesServiceImpl extends BaseServiceImpl<Resources>{
	@Autowired
	private ResourcesMapper resourcesMapper;
	@Autowired
	private RoleResourcesMapper roleResourcesMapper;
	

	public int delete(Long id) {
		//if("1".equals(id) || id == 1){
		if(id == null || id == 1){
			return 0 ;
		}
		RoleResources[] r=new RoleResources[1];
		RoleResources roleResources=new RoleResources();
		roleResources.setResourcesId(id);
		r[0]=roleResources;
		roleResourcesMapper.deleteByT(r);
		resourcesMapper.deleteByPrimaryKey(new Long[]{id});
		return 0;
	}
	

	public int insert(Resources resources) {
	//	String id = UUID.randomUUID().toString();
	//	resources.setId(id);
		if("".equals(resources.getUrl())){
			resources.setUrl("/");
		}
		return resourcesMapper.insert(resources);
	}

	public Resources save(Resources resources) {
		if("".equals(resources.getUrl())){
			resources.setUrl("/");
		}
		 resourcesMapper.insert(resources);
		return resources;
	}


	public int updateByPrimaryKey(Resources resources) {
		if("".equals(resources.getUrl())){
			resources.setUrl("/");
		}
		return resourcesMapper.updateByPrimaryKey(resources);
	}

	public Resources update(Resources resources) {
		if("".equals(resources.getUrl())){
			resources.setUrl("/");
		}
		resourcesMapper.updateByPrimaryKey(resources);
		return resources;
	}


	public List<JsonTreeData> findResources() {
		List<Resources> resourcesList =resourcesMapper.selectAll();
		 List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();
        /*为了整理成公用的方法，所以将查询结果进行二次转换。
         * 其中specid为主键ID，varchar类型UUID生成
         * parentid为父ID
         * specname为节点名称
         * */
       for (Resources htSpecifications : resourcesList) {
           JsonTreeData treeData = new JsonTreeData();
           treeData.setId(htSpecifications.getId()==null?"":String.valueOf(htSpecifications.getId()));
           treeData.setPid(htSpecifications.getPid()==null?"":String.valueOf(htSpecifications.getPid()));
           treeData.setText(htSpecifications.getName());
           Attributes attributes=new Attributes();
           attributes.setUrl(htSpecifications.getUrl()); 
           treeData.setAttributes(attributes);
		   treeData.setName(htSpecifications.getName());
		   treeData.setUrl(htSpecifications.getUrl());
		   treeData.setType(htSpecifications.getType());
		   treeData.setSort(htSpecifications.getSort());
           treeDataList.add(treeData);
       }
       //最后得到结果集,经过FirstJSON转换后就可得所需的json格式
       List<JsonTreeData> newTreeDataList = TreeNodeUtil.getfatherNode(treeDataList);
		return newTreeDataList;
	}
	

	public List<JsonTreeData> findResourcesEMUByResources(User user) {
		Resources resources=new Resources();
		resources.setId(user.getId());
		resources.setType("1");
		List<Resources> resourcesList =resourcesMapper.findTByT(resources);
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();
         /*为了整理成公用的方法，所以将查询结果进行二次转换。
          * 其中specid为主键ID，varchar类型UUID生成
          * parentid为父ID
          * specname为节点名称
          * */
        for (Resources htSpecifications : resourcesList) {
            JsonTreeData treeData = new JsonTreeData();
            treeData.setId(htSpecifications.getId()==null?"":String.valueOf(htSpecifications.getId()));
            treeData.setPid(htSpecifications.getPid()==null?"":String.valueOf(htSpecifications.getPid()));
            treeData.setText(htSpecifications.getName());
            Attributes attributes=new Attributes();
            attributes.setUrl(htSpecifications.getUrl()); 
            treeData.setAttributes(attributes);
            treeDataList.add(treeData);
        }
        //最后得到结果集,经过FirstJSON转换后就可得所需的json格式
        List<JsonTreeData> newTreeDataList = TreeNodeUtil.getfatherNode(treeDataList);
		return newTreeDataList;
	}
	
}
