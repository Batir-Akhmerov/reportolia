/**
 * 
 */
package com.reportolia.core.utils;

import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;


/**
 * The ListUtils class
 *
 * @author Batir Akhmerov
 * Created on Dec 3, 2015
 */
public class ListUtils {
	
	public static<T extends Object> int indexByProperty(List<T> list, String property, Object value) {
		if (CollectionUtils.isEmpty(list)) {
			return -1;
		}
		int i = 0;
		try {
			for (T bean: list) {
				Object v = BeanUtils.getProperty(bean, property);
				if (v == value || (value.equals(v))) {
					return i;
				}
				i++;
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		return -1;
	}
	
	public static<T extends Object> T findByProperty(List<T> list, String property, Object value) {
		int index = indexByProperty(list,property, value);
		if (index == -1) {
			return null;
		}
		return list.get(index);
	}
	
	public static<T extends Object> boolean containsByProperty(List<T> list, String property, Object value) {
		int index = indexByProperty(list,property, value);
		return index != -1;
	}
	
	public static<T extends Object> int getSize(List<T> list) {
		if (list == null) {
			return -1;
		}
		return list.size();
	}
	
	public static<T extends Object> Collection<?> extractPropertyValues(List<T> list, String propertyName) {
		return CollectionUtils.collect(list, new BeanToPropertyValueTransformer(propertyName));
	}

}
