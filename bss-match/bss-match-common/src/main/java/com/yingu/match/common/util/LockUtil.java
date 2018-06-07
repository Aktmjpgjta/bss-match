package com.yingu.match.common.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** 内存锁工具
 * Description:
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2011-12-27    lirong       1.0        1.0 Version
 *
 * @author lirong
 * @version 1.0
 */
public class LockUtil {

    private static final ConcurrentMap<Object, Object> map = new ConcurrentHashMap<Object, Object>();
    private static final LockUtil instance = new LockUtil();

	/**
     * 默认构造器
     */
    private LockUtil() {
    }

    public static LockUtil getInstance() {
        return instance;
    }

    public  Object put(Object key, Object value) {
        return map.put(key, value);
    }

    public  Object remove(Object key) {
        return map.remove(key);
    }

    public  Object get(Object key) {
        return map.get(key);
    }
    
    /**
     * 获取存储内存锁的Map
     * @return ConcurrentMap 内存锁Map
     */
    public ConcurrentMap<Object, Object> getLockMap() {
    	return map;
    }
}
