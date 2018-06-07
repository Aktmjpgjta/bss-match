package com.yingu.match.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yingu.match.common.service.impl.BaseServiceImpl;
import com.yingu.match.sys.dao.UserMapper;
import com.yingu.match.sys.dao.UserRoleMapper;
import com.yingu.match.sys.model.User;
import com.yingu.match.sys.model.UserRole;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User>{
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	public List<UserRole> findUserRoleByUser(User user) {	
		UserRole userRole=new UserRole();
		userRole.setUserId(user.getId());
		return userRoleMapper.findTByT(userRole);
	}

	public int insert(User user,Long[] roleId) {
		int rs = userMapper.findTCountByT(user);
		if(rs > 0){
			return 0;
		}
	//	String id = UUID.randomUUID().toString();
	//	user.setId(id);
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		int result = super.insert(user);
		if(roleId!=null){
			for(int i=0;i<roleId.length;i++){
				UserRole userRole=new UserRole();
			//	userRole.setId(UUID.randomUUID().toString());
				userRole.setUserId(user.getId());
				userRole.setRoleId(roleId[i]);
				userRoleMapper.insert(userRole);
			}
		}
		return result;
	}

	public int update(User user,Long[] roleId) {
		if(user.getPassword()!=null && !"".equals(user.getPassword())){
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		}
		UserRole userRole2=new UserRole();
		userRole2.setUserId(user.getId());
		userRoleMapper.deleteByT(new UserRole[]{userRole2});
		if(roleId!=null){
			for(int i=0;i<roleId.length;i++){
				UserRole userRole=new UserRole();
			//	userRole.setId(UUID.randomUUID().toString());
				userRole.setUserId(user.getId());
				userRole.setRoleId(roleId[i]);
				userRoleMapper.insert(userRole);
			}
		}
		user.setModifyTime(new Date());
		return userMapper.updateByPrimaryKey(user);
	}

	public int delete(Long ids) {
		Long[] id = {ids};
		UserRole[] u=new UserRole[id.length];
		for(int i=0;i<id.length;i++){
			UserRole user=new UserRole();
			user.setUserId(id[i]);
			u[i]=user;
		}
		userRoleMapper.deleteByT(u);
		userMapper.deleteByPrimaryKey(id);
		
		return 0;
	}

	public User selectByKey(Long id){
		User u=userMapper.selectByPrimaryKey(id);
		u.setPassword("");
		return u;
	}

	public int resetPwd(User user) {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setModifyTime(new Date());
		return userMapper.updateByPrimaryKey(user);
	}
}
