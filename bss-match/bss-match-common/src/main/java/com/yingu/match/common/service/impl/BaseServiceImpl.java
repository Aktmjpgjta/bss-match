package com.yingu.match.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yingu.match.common.dao.BaseMapper;
import com.yingu.match.common.model.BaseModel;
import com.yingu.match.common.model.Page;
import com.yingu.match.common.service.BaseService;
import com.yingu.match.common.util.CoreUtil;
import com.yingu.match.common.util.ModelUtil;

@Service
@Transactional
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T>{
	@Autowired
    protected BaseMapper<T> baseMapper;
	@Override
	public List<T> selectAll(){
		return baseMapper.selectAll();
		
	}
	@Override
	public T selectByPrimaryKey(Long id){
		return baseMapper.selectByPrimaryKey(id);
		
	}
	@Override
    public int insert(T t){
		ModelUtil.setCommonFields(t); //填充公共字段
    	return baseMapper.insert(t);
    }
	@Override
    public int updateByPrimaryKey(T t){
		t.setModifyTime(CoreUtil.generateTimestamp());//修改时间
    	return baseMapper.updateByPrimaryKey(t);
    	
    }
    @Override
    public int deleteByPrimaryKey(String ids){
    	String temp[] = ids.split(",");
    	Long[] id = new Long[temp.length];
    	for(int i=0; i<temp.length; i++){
    		id[i] = Long.valueOf(temp[i]);
    	}
    	return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
	public Page<T> findTByPage(Page<T> page,T t) {	
    	page.setT(t);
		List<T> userList=baseMapper.findTByPage(page);
		page.setResult(userList);
		int  count=baseMapper.findTCountByT(page.getT());
		page.setTotalCount(count);
		return page;
	}
    @Override
    public List<T> findTByT(T t){
    	
		return baseMapper.findTByT(t);
    }
    
    @Override
    public T findTByTOne(T t){
    	List<T> tOne=baseMapper.findTByT(t);
    	if(tOne.size()>0){
    		return tOne.get(0);
    	}else{
    		return null;
    	}
		
    }
	
}
