/**
 * 
 */
package com.reportolia.core.web.controllers.jtable;

/**
 * The BaseJsonResult class is used as data transfer object between MVC and JQuery JTable plugin.
 * Sorry, class fields do not meet Java Bean Naming Convention and violate Java encapsulation rules 
 * in order to satisfy ASP-oriented names expected by JTable. 
 *
 * @author Batir Akhmerov
 * Created on Oct 17, 2016
 */
public class BaseJsonResult {
	
	public static final String RESULT_OK = "OK";
	public static final String RESULT_ERROR = "ERROR";
	
	public String Result; 
	public String Message;
	
	public BaseJsonResult() {
		this.Result = RESULT_OK;
	}
	
	public BaseJsonResult(String error) {
		this.Result = RESULT_ERROR;
		this.Message = error;
	}
}
