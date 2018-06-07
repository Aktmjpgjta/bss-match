package com.yingu.match.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yingu.match.common.service.impl.BaseServiceImpl;
import com.yingu.match.common.util.DateUtil;
import com.yingu.match.common.util.ModelUtil;
import com.yingu.match.sys.dao.SerialNumberMapper;
import com.yingu.match.sys.model.SerialNumber;

/**
 * 
 * @Description: 流水号生成服务
 * @author:	daichangfu
 * @date:	2017年7月3日 上午11:38:14
 */
@Service("serialNumberService")
@Transactional
public class SerialNumberServiceImpl extends BaseServiceImpl<SerialNumber> {

	@Autowired
	private SerialNumberMapper serialNumberMapper;
	
	/**
	 * 
	 * @Description: 生成新的流水池
	 * @param: snKey 流水号关键字
	 * @return: List<Long>
	 * @throws  Exception
	 * @author:	daichangfu
	 * @date:	2017年7月3日 下午2:07:17
	 */
	public synchronized List<Long> genNumRange(String snKey) throws Exception{
		
		SerialNumber snm = serialNumberMapper.findBySnKey(snKey);
		if(snm == null){
			throw new Exception("指定流水号配置不存在："+snKey);
		}
		int snType = snm.getSnType();
		//由当前范围生成完整标识
		String currRange = genBlong(snType);
		
		List<Long> rtnList = new ArrayList<Long>();
		//所属时间内的流水不存在，则重置该流水
		if(!currRange.equals(snm.getCurrRange())){
			snm.setCurrRange(currRange);
			snm.setNextNum(1l);
		}
		
		//获取流水段
		long sn1 = snm.getNextNum();
		int offset = snm.getPoolSize();
		if(offset<1){
			offset = 1;
			snm.setPoolSize(offset);
		}
		for(int i=0; i<offset; i++){
			rtnList.add(sn1+i);
		}
		
		snm.setNextNum(sn1+offset);
		
		super.updateByPrimaryKey(snm);
		
		return rtnList;
	}
	
	
	/**
	 * 
	 * @Description: 生成所属标识
	 * @param:
	 * @return:
	 * @author:	daichangfu
	 * @date:	2017年7月3日 下午6:12:29
	 */
	private String genBlong(int snType) {
		String currRange = null;
		Date now = new Date();
		switch(snType){
		case SerialNumber.SN_TYPE_DAY:
			currRange = DateUtil.date2Str(now, DateUtil.FORMAT_YEAR_MONTH_DAY);
			break;
		case SerialNumber.SN_TYPE_MONTH:
			currRange = DateUtil.date2Str(now, DateUtil.FORMAT_YEAR_MONTH);
			break;
		case SerialNumber.SN_TYPE_YEAR:
			currRange = DateUtil.date2Str(now, DateUtil.FORMAT_YEAR);
			break;
		default:
			currRange = SerialNumber.SN_TYPE_PERM_TEXT;
			break;
		}
		return currRange;
	}
	
	/**
	 * 新增流水号
	 */
	public int insert(SerialNumber serialNumber) {
		SerialNumber sn = serialNumberMapper.findBySnKey(serialNumber.getSnKey());
		if(sn != null){
			return 0;
		}
		ModelUtil.setCommonFields(serialNumber);
		serialNumber.setCurrRange(DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
		serialNumberMapper.insert(serialNumber);
		return 1;
	}
	
	/**
	 * 
	 * @Description:修改流水号
	 * @param: serialNumber
	 * @author:	daichangfu
	 * @date:	2017年11月25日 上午11:00:16
	 */
	public void update(SerialNumber serialNumber) {
		SerialNumber sn = serialNumberMapper.selectByPrimaryKey(serialNumber.getId());
		sn.setSnName(serialNumber.getSnName());
		sn.setSnKey(serialNumber.getSnKey());
		sn.setCurrRange(serialNumber.getCurrRange());
		sn.setNextNum(serialNumber.getNextNum());
		sn.setPoolSize(serialNumber.getPoolSize());
		updateByPrimaryKey(sn);
	}

}
