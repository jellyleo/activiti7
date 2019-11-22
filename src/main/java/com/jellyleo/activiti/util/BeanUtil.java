package com.jellyleo.activiti.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * 功能描述:
 *
 * @author Jelly
 * @created 2019年11月22日
 * @version 1.0.0
 */
public class BeanUtil {

	/**
	 * 
	 * 功能描述:
	 *
	 * @param map
	 * @param beanType
	 * @return
	 * @throws Exception
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static <T> T mapToBean(Map<Object, Object> map, Class<T> beanType) throws Exception {
		T t = beanType.newInstance();
		PropertyDescriptor[] pds = Introspector.getBeanInfo(beanType, Object.class).getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			for (Entry<?, ?> entry : map.entrySet()) {
				if (entry.getKey().equals(pd.getName())) {
					pd.getWriteMethod().invoke(t, entry.getValue());
				}
			}
		}
		return t;
	}

	/**
	 * 
	 * 功能描述:
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static Map<String, Object> beanToMap(Object bean) throws Exception {

		Map<String, Object> map = new HashMap<>();
		BeanInfo info = Introspector.getBeanInfo(bean.getClass(), Object.class);
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			String key = pd.getName();
			Object value = pd.getReadMethod().invoke(bean);
			map.put(key, value);
		}
		return map;
	}
}