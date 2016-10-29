/**
 * 
 */
package com.reportolia.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;

import com.reportolia.core.utils.functions.InfoMapper;
import com.reportolia.core.utils.functions.Mapper;


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
				Object v = PropertyUtils.getProperty(bean, property);
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
			return 0;
		}
		return list.size();
	}
	
	public static<T extends Object> boolean isEmpty(List<T> list) {
		return getSize(list) == 0;
	}
	
	public static<T extends Object> Collection<?> extractPropertyValues(List<T> list, String propertyName) {
		return CollectionUtils.collect(list, new BeanToPropertyValueTransformer(propertyName));
	}
	
	public static<T extends Object> void setProperty(List<T> list, String property, Object value) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		
		try {
			for (T bean: list) {
				PropertyUtils.setProperty(bean, property, value);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static<T extends Object> T getFirst(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	public static<T extends Object> List<T> safeList(List<T> list) {
		List<T> res = new ArrayList<>();
		if (isEmpty(list)) {
			return res;
		}
		return list;
	}
	
	public static <F extends Object, T extends Object> List<T> remap(List<F> list, Mapper<F,T> mapper) {
		List<T> resList = new ArrayList<>();
		for (F bean: safeList(list)) {
			resList.add(mapper.map(bean));
		}
		return resList;
	}
	
	public static <F extends Object, I extends Object, T extends Object> List<T> remap(List<F> list, I info, InfoMapper<F,I, T> mapper) {
		List<T> resList = new ArrayList<>();
		for (F bean: safeList(list)) {
			resList.add(mapper.map(info, bean));
		}
		return resList;
	}
	

}
