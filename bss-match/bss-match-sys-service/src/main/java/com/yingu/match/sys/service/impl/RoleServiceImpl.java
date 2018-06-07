package com.yingu.match.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yingu.match.common.service.impl.BaseServiceImpl;
import com.yingu.match.sys.dao.RoleMapper;
import com.yingu.match.sys.dao.RoleResourcesMapper;
import com.yingu.match.sys.dao.UserRoleMapper;
import com.yingu.match.sys.model.Role;
import com.yingu.match.sys.model.RoleResources;
import com.yingu.match.sys.model.UserRole;


@Service("roleService")
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleResourcesMapper roleResourcesMapper;
	
	public List<Role> selectAll() {
		
		return roleMapper.selectAll();
	}


	public int insert(Role role, Long[] resourcesId) {
	//	String id = UUID.randomUUID().toString();
	//	role.setId(id);
		
		int result = roleMapper.insert(role);
		
		if(resourcesId!=null){
			for(int i=0;i<resourcesId.length;i++){
				RoleResources roleResources=new RoleResources();
			//	roleResources.setId(UUID.randomUUID().toString());
				roleResources.setResourcesId(resourcesId[i]);
				roleResources.setRoleId(role.getId());
				roleResourcesMapper.insert(roleResources);
			}
		}
		
		return result;
	}

	public int update(Role role,Long[] resourcesId) {
		RoleResources roleResources2=new RoleResources();
		roleResources2.setRoleId(role.getId());
		roleResourcesMapper.deleteByT(new RoleResources[]{roleResources2});
		if(resourcesId!=null){
			for(int i=0;i<resourcesId.length;i++){
				RoleResources roleResources=new RoleResources();
			//	roleResources.setId(UUID.randomUUID().toString());
				roleResources.setRoleId(role.getId());
				roleResources.setResourcesId(resourcesId[i]);
				roleResourcesMapper.insert(roleResources);
			}
		}
		return roleMapper.updateByPrimaryKey(role);
	}

	public int delete(Long id) {
		Long[] id_arr = new Long[]{id};
		RoleResources[] r=new RoleResources[id_arr.length];
		UserRole[] u=new UserRole[id_arr.length];
		for(int i=0;i<id_arr.length;i++){
			RoleResources roleResources=new RoleResources();
			UserRole userRole=new UserRole();
			roleResources.setRoleId(id_arr[i]);
			userRole.setRoleId(id_arr[i]);
			r[i]=roleResources;
			u[i]=userRole;
		}
		userRoleMapper.deleteByT(u);
		roleResourcesMapper.deleteByT(r);
		return roleMapper.deleteByPrimaryKey(id_arr);
	}


	public List<RoleResources> findRoleResourcesByRole(Role role) {
		RoleResources roleResources=new RoleResources();
		roleResources.setRoleId(role.getId());
		return roleResourcesMapper.findTByT(roleResources);
	}

}
