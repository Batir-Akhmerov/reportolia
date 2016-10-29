/**
 * 
 */
package com.reportolia.core.utils;

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
}
