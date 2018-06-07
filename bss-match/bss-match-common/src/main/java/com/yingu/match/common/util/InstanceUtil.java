package com.yingu.match.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * 实例辅助类
 * 
 * @author HC
 * @since 2012-07-18
 */
public final class InstanceUtil {
    protected static Logger logger = LoggerFactory.getLogger(InstanceUtil.class);

    private InstanceUtil() {
    }

    /** 实例化并复制属性 */
    public static final <T> T to(Object orig, Class<T> clazz) {
        T bean = null;
        try {
            bean = clazz.newInstance();
            PropertyUtils.copyProperties(bean, orig);
        } catch (Exception e) {
        }
        return bean;
    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {
        Map<String, Object> map = newHashMap();
        if (obj == null) {
            return map;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.error("transMap2Bean Error ", e);
        }
        return map;
    }

    private static Map<String, MethodAccess> methodMap = new HashMap<String, MethodAccess>();

    /**
     * 执行某对象方法
     * 
     * @param owner
     *            对象
     * @param methodName
     *            方法名
     * @param args
     *            参数
     * @return 方法返回值
     */
    public static final Object invokeMethod(Object owner, String methodName, Object... args) {
        Class<?> ownerClass = owner.getClass();
        String key = null;
        if (args != null) {
            Class<?>[] argsClass = new Class[args.length];
            for (int i = 0, j = args.length; i < j; i++) {
                if (args[i] != null) {
                    argsClass[i] = args[i].getClass();
                }
            }
            key = ownerClass + "_" + methodName + "_" + StringUtils.join(argsClass, ","); // 用于区分重载的方法
        } else {
            key = ownerClass + "_" + methodName; // 用于区分重载的方法
        }
        MethodAccess methodAccess = methodMap.get(key);
        if (methodAccess == null) {
            methodAccess = MethodAccess.get(ownerClass);
            methodMap.put(key, methodAccess); // 缓存Method对象
        }
        return methodAccess.invoke(owner, methodName, args);
    }

    /**
     * Constructs an empty ArrayList.
     */
    public static final <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    /**
     * Constructs an empty ArrayList.
     */
    @SuppressWarnings("unchecked")
    public static final <E> ArrayList<E> newArrayList(E... e) {
        ArrayList<E> list = new ArrayList<E>();
        Collections.addAll(list, e);
        return list;
    }

    /**
     * Constructs an empty HashMap.
     */
    public static final <k, v> HashMap<k, v> newHashMap() {
        return new HashMap<k, v>();
    }

    /**
     * Constructs an empty HashSet.
     */
    public static final <E> HashSet<E> newHashSet() {
        return new HashSet<E>();
    }

    /**
     * Constructs an empty Hashtable.
     */
    public static final <k, v> Hashtable<k, v> newHashtable() {
        return new Hashtable<k, v>();
    }

    /**
     * Constructs an empty LinkedHashMap.
     */
    public static final <k, v> LinkedHashMap<k, v> newLinkedHashMap() {
        return new LinkedHashMap<k, v>();
    }

    /**
     * Constructs an empty LinkedHashSet.
     */
    public static final <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet<E>();
    }

    /**
     * Constructs an empty LinkedList.
     */
    public static final <E> LinkedList<E> newLinkedList() {
        return new LinkedList<E>();
    }

    /**
     * Constructs an empty TreeMap.
     */
    public static final <k, v> TreeMap<k, v> newTreeMap() {
        return new TreeMap<k, v>();
    }

    /**
     * Constructs an empty TreeSet.
     */
    public static final <E> TreeSet<E> newTreeSet() {
        return new TreeSet<E>();
    }

    /**
     * Constructs an empty Vector.
     */
    public static final <E> Vector<E> newVector() {
        return new Vector<E>();
    }

    /**
     * Constructs an empty WeakHashMap.
     */
    public static final <k, v> WeakHashMap<k, v> newWeakHashMap() {
        return new WeakHashMap<k, v>();
    }

    /**
     * Constructs an empty HashMap.
     */
    public static final <k, v> Map<k, v> newHashMap(k key, v value) {
        Map<k, v> map = newHashMap();
        map.put(key, value);
        return map;
    }

    /**
     * Constructs an empty ConcurrentHashMap.
     */
    public static final <k, v> ConcurrentHashMap<k, v> newConcurrentHashMap() {
        return new ConcurrentHashMap<k, v>();
    }
}
