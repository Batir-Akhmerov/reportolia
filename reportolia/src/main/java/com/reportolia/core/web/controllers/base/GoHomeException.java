/**
 * 
 */
package com.reportolia.core.web.controllers.base;

/**
 * The GoHomeException class
 *
 * @author Batir Akhmerov
 * Created on Dec 29, 2016
 */
public class GoHomeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public GoHomeException() {
		
	}
	
	public GoHomeException(String msg) {
		super(msg);
	}
}
