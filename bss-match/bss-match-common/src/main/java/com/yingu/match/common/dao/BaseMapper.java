package com.yingu.match.common.dao;

import java.util.List;

import com.yingu.match.common.model.Page;

public interface BaseMapper<T> {
    int deleteByPrimaryKey(Long[] ids);

    int insert(T t);

    T selectByPrimaryKey(Long id);

    List<T> selectAll();

    int updateByPrimaryKey(T t);

	List<T> findTByPage(Page<T> page);

	int findTCountByT(T t);

	List<T> findTByT(T t);
	
	int deleteByT (T[] Ts);
}