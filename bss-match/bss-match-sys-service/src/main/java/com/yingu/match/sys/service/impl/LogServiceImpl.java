package com.yingu.match.sys.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yingu.match.common.service.impl.BaseServiceImpl;
import com.yingu.match.sys.model.Log;

@Service("logService")
@Transactional
public class LogServiceImpl extends BaseServiceImpl<Log>{
	
}
