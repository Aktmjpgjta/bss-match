package com.yingu.match.sys.model;

import com.yingu.match.common.model.BaseModel;

public class SerialNumber extends BaseModel{

	public static final int SN_TYPE_PERM = 0;//永久流水
	public static final String SN_TYPE_PERM_TEXT = "永久";//永久流水
	public static final int SN_TYPE_YEAR = 1;//按年流水
	public static final int SN_TYPE_MONTH = 2;//按月流水
	public static final int SN_TYPE_DAY = 3;//按天流水
	
    /**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String snName; //流水号名称

	private String snKey;//序列关键字

    private Integer snType;//序列类型

    private String currRange;//当前范围：如2013、2013-03、2013-03-06

    private Long nextNum = 1l;//当前流水;

    private Integer poolSize = 1;//池大小

    public String getSnKey() {
        return snKey;
    }

    public void setSnKey(String snKey) {
        this.snKey = snKey == null ? null : snKey.trim();
    }

    public Integer getSnType() {
        return snType;
    }

    public void setSnType(Integer snType) {
        this.snType = snType;
    }

    public String getCurrRange() {
        return currRange;
    }

    public void setCurrRange(String currRange) {
        this.currRange = currRange == null ? null : currRange.trim();
    }

    public Long getNextNum() {
        return nextNum;
    }

    public void setNextNum(Long nextNum) {
        this.nextNum = nextNum;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

	public String getSnName() {
		return snName;
	}

	public void setSnName(String snName) {
		this.snName = snName;
	}

}