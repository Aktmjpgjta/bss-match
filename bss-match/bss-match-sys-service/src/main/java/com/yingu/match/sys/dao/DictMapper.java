package com.yingu.match.sys.dao;

import java.util.List;

import com.yingu.match.common.dao.BaseMapper;
import com.yingu.match.sys.model.Dict;

public interface DictMapper extends BaseMapper<Dict>{
	public List<Dict> findDictByPathLike(String path);
	public int deleteByPathLike(String path);
}