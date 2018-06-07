package com.yingu.match.common.service;

import java.util.List;

import com.yingu.match.common.model.Page;




public interface BaseService<T> {
	
	List<T> selectAll();
	
	T selectByPrimaryKey(Long id);
	
    int insert(T t);
    
    int updateByPrimaryKey(T t);
    
    int deleteByPrimaryKey(String ids);

    Page<T> findTByPage(Page<T> page, T t);
	
    List<T> findTByT(T t);
    
    T findTByTOne(T t);
	
}
