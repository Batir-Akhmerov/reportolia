/**
 * 
 */
package com.reportolia.core.web.controllers.base.datatable;

import java.util.ArrayList;
import java.util.List;

/**
 * The JsonDatatableResult class
 *
 * @author Batir Akhmerov
 * Created on Nov 5, 2016
 */
public class JsonDatatableResult<T extends Object> extends JsonResult {
	
	private List<T> data;	
	private int recordsTotal;
	private Integer recordsFiltered;
	

	public JsonDatatableResult(){
		//
	}
	
	public JsonDatatableResult(List<T> data, JsonForm form){
		super(form);
		this.data = data;
	}
	public JsonDatatableResult(String error, JsonForm form){
		super(form);
		super.setError(error);
		this.data = new ArrayList<>();
	}
	
	
	public List<T> getData() {
		return this.data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getRecordsTotal() {
		return this.recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return this.recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

}
