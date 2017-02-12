/**
 * 
 */
package com.reportolia.core.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * The CoreUtils class
 *
 * @author Batir Akhmerov
 * Created on Oct 27, 2016
 */
public class CoreUtils {
	
	public static boolean isKeyNull(Long key) {
		return key == null || key == 0;
	}
	
	
	public static String format(String msg, Object ... args) {
		return String.format(msg, args);
	}

	public static void copyProperties(final Object source, final Object target, final String...properties){
		final BeanWrapper src = new BeanWrapperImpl(source);
		final BeanWrapper trg = new BeanWrapperImpl(target);
		
		for(final String propertyName : properties){
			trg.setPropertyValue(
				propertyName,
				src.getPropertyValue(propertyName)
			);
		}

	}
	
}
