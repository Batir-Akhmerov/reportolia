/**
 * 
 */
package com.reportolia.core.web.controllers.jtable;

import java.util.List;

/**
 * The JsonResult class is used as data transfer object between MVC and JQuery JTable plugin.
 * Sorry, class fields do not meet Java Bean Naming Convention and violate Java encapsulation rules 
 * in order to satisfy ASP-oriented names expected by JTable. 
 *
 * @author Batir Akhmerov
 * Created on Oct 17, 2016
 */
public class JsonResult<T extends Object> extends BaseJsonResult{
	
	public static final String RESULT_OK = "OK";
	
	public List<T> Records;
	public T Record;
	public List<T> Options;
	public Integer TotalRecordCount;
	
	
	public JsonResult() {
		super();
	}
	public JsonResult(String error) {
		super(error);
	}
	
	public JsonResult(List<T> list) {
		this(list, false);
	}
	public JsonResult(List<T> list, boolean isOptions) {
		this.Result = RESULT_OK;
		if (isOptions) {
			this.Options = list;
		}
		else {
			this.Records = list;
		}
	}
	public JsonResult(T record) {
		this.Result = RESULT_OK;
		this.Record = record;
	}
	
}
