package com.yingu.match.sys.dao;

import com.yingu.match.common.dao.BaseMapper;
import com.yingu.match.sys.model.SerialNumber;

public interface SerialNumberMapper extends BaseMapper<SerialNumber>{

	public SerialNumber findBySnKey(String snKey);
}