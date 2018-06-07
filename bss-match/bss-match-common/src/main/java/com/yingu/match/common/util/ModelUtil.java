package com.yingu.match.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import com.yingu.match.common.model.BaseModel;

/**
 * 模型工具类
 * @author liufei
 *
 */
public class ModelUtil {
	private static Logger log = Logger.getLogger(ModelUtil.class);
	private ModelUtil(){}
	/**
	 * 得到模型集合的ID集
	 * @param col
	 * @return
	 */
	public static Set<Long> getIdSet(Collection<? extends BaseModel> col){
		Set<Long> idSet = new HashSet<Long>();
		for(BaseModel model : col){
			idSet.add(model.getId());
		}
		return idSet;
	}
	
	/**
	 * 拷贝非空属性(排险)
	 * @param source
	 * @param dest
	 */
	@SuppressWarnings("rawtypes")
	public static void copyProps(Object source, Object target, String...ignorProps) {
		if(source instanceof Map){
			try {
				Map sourceMap = (Map)source;
				Set<String> ignorPropsSet = new HashSet<String>();
				if(null!=ignorProps){
					for(String prop : ignorProps){
						ignorPropsSet.add(prop);
					}
				}
				
				for(Object key : sourceMap.keySet()){
					if(null==key || ignorPropsSet.contains(key)){
						continue;
					}else{
						Object value = sourceMap.get(key);
						if(PropertyUtils.isWriteable(target, key.toString())){
							PropertyUtils.setProperty(target, key.toString(), value);
						}
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else{
			org.springframework.beans.BeanUtils.copyProperties(source, target, ignorProps);
		}
	}
	
	/**
	 * 拷贝非空属性(包含)
	 * @param source
	 * @param dest
	 */
	public static void copyPropsInc(Object source, Object target, String...incProps) {
		try {
			Set<String> incPropsSet = new HashSet<String>();
			if(null!=incProps){
				for(String prop : incProps){
					incPropsSet.add(prop);
				}
			}
			
			for(PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(source)){
				String key = pd.getName();
				if(null==key || !incPropsSet.contains(key)){
					continue;
				}else{
					Object value = PropertyUtils.getProperty(source, key);
					if(PropertyUtils.isWriteable(target, key.toString())){
						PropertyUtils.setProperty(target, key.toString(), value);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 模型列表排序
	 * @param list
	 * @param isAsc 是否为升序
	 * @return
	 */
	public static List<? extends BaseModel> sort(List<? extends BaseModel> list, boolean isAsc){
		Comparator<BaseModel> comp = null;
		if(isAsc){
			comp = new Comparator<BaseModel>(){
				public int compare(BaseModel o1, BaseModel o2) {
					return (int)(o1.getSortidx()-o2.getSortidx());
				}
			};
		}else{
			comp = new Comparator<BaseModel>(){
				public int compare(BaseModel o1, BaseModel o2) {
					return -1 * (int)(o1.getSortidx()-o2.getSortidx());
				}
			};
		}
		Collections.sort(list, comp);
		
		return list;
	}
	/**
	 * 使用bean中的属性创建Map
	 * @param bean
	 * @param propNames 需要放到map中的属性名称
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> convBeanToMap(Object bean, String...propNames) {
		Assert.notNull(propNames, "请指定属性名称");
		Map<String,Object> rtn = new HashMap<String,Object>();
		if(propNames.length==0 || "*".equals(propNames[0])){
			PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(bean);
			for(PropertyDescriptor propDesc : propDescs){
				String propName = propDesc.getName();
				Object propValue = null;
				try {
					propValue = PropertyUtils.getProperty(bean, propName);
				} catch (Exception e) {
					log.debug("获取其它属性时出错："+e.getMessage());
				}
				rtn.put(propName, propValue);
			}
		}else{
			for(String propName: propNames){
				Object propValue = null;
				try{
					if(PropertyUtils.isReadable(bean, propName)){
						propValue = PropertyUtils.getProperty(bean, propName);
					}
				} catch (Exception e){
					log.debug("获取其它属性时出错："+e.getMessage());
				}
				rtn.put(propName, propValue);
			}
		}
		
		return rtn;
	}
	/**
	 * 取对象的属性值
	 * @param obj
	 * @param propName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Object getPropertyValue(Object obj, String propName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return PropertyUtils.getProperty(obj, propName);
	}
	
	/**
	 * 
	 * Description: 移除空关系（即关联对象ID无效时移除该关系）
	 *
	 * @param 
	 * @return Object
	 * @throws 
	 * @Author liufei
	 * Create Date: 2012-4-27 上午11:58:37
	 */
	public static Object removeNullRelation(Object obj, String relationIdName) throws Exception{
		int idx = relationIdName.lastIndexOf(".");
		Assert.isTrue(idx>0, "关系id属性名无效："+relationIdName);
		
		String relationName = relationIdName.substring(0,idx);
		Object relationId = PropertyUtils.getProperty(obj, relationIdName);
		if(relationId instanceof String){
			String id = (String)relationId;
			if(null==id || id.trim().length()==0){
				PropertyUtils.setProperty(obj, relationName, null);
			}
		}else{
			if(null==relationId){
				PropertyUtils.setProperty(obj, relationName, null);
			}
		}
		return obj;
	}
	
	/**
	 * 
	 * Description: 将树结构转换为列表结构，叶子节点在前
	 *
	 * @param 
	 * @return List
	 * @throws 
	 * @Author fei
	 * Create Date: 2013-8-30 上午11:41:16
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List treeToList(Object model, String subPropName, List list) throws Exception{
		Collection subs = (Collection)PropertyUtils.getProperty(model, subPropName);
		if(null!=subs && subs.size()>0){
			for(Object sub : subs){
				treeToList(sub, subPropName, list);
			}
		}
		list.add(model);
		
		return list;
	}
	
	/**
	 * 
	 * Description: 为String类型的属性做trim
	 *
	 * @param 
	 * @return void
	 * @throws 
	 * @Author liufei
	 * Create Date: 2013-11-16 下午3:17:28
	 */
	public static void trimFields(Object model, String... propNames) throws Exception{
		Assert.notEmpty(propNames, "请指定要去前后空格的属性名");
		for(String propName : propNames){
			Object val = PropertyUtils.getProperty(model, propName);
			if(val instanceof String){
				if(null!=val){
					String valStr = (String)val;
					valStr = valStr.trim();
					PropertyUtils.setProperty(model, propName, valStr);
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 填充公共字段
	 * @author:	daichangfu
	 * @date:	2017年6月29日 下午2:33:42
	 */
	public static void setCommonFields(BaseModel model){
		model.setSortidx(CoreUtil.generateSortIdx());//排序号
		Timestamp nowTime = CoreUtil.generateTimestamp();//当前时间
		model.setVersion(0);//版本号
		model.setCreateTime(nowTime);//创建时间
		model.setModifyTime(nowTime);//修改时间
	}
	
	/**
	 * 
	 * @Description: 拷贝非空对象属性值
     * @param source 源对象 
     * @param target 目标对象 
	 * @author:	daichangfu
	 * @date:	2017年7月12日 下午4:39:08
	 */
	public static void copyPropertiesIgnoreNull(Object source, Object target, String... ignoreProperties) {
        final BeanWrapper src = new BeanWrapperImpl(source);  
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();  
   
        Set<String> emptyNames = new HashSet<String>();  
        for (java.beans.PropertyDescriptor pd : pds) {  
            Object srcValue = src.getPropertyValue(pd.getName());  
            if (srcValue == null)  
                emptyNames.add(pd.getName());  
        }
        for(String sr:ignoreProperties){
        	emptyNames.add(sr);
        }
        String[] result = new String[emptyNames.size()];
        BeanUtils.copyProperties(source, target, emptyNames.toArray(result));  
    }
	
}
