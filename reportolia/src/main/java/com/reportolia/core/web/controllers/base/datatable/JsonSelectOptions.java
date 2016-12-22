/**
 * 
 */
package com.reportolia.core.web.controllers.base.datatable;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * The JsonSelectOptions class
 *
 * @author Batir Akhmerov
 * Created on Dec 4, 2016
 */
public class JsonSelectOptions<T extends Object> extends JsonResult {

	private List<T> items;
	private long total_count;
	private Boolean inclomplete_results;
	
	public JsonSelectOptions() {
		
	}
	public JsonSelectOptions(Page<T> items, Integer totalCount) {
		this(items.getContent(), items.getTotalElements());
	}
	
	public JsonSelectOptions(List<T> items, Long totalCount) {
		this.items = items;
		if (totalCount != null) {
			this.total_count = totalCount;
		}
		else if (items != null){
			this.total_count = items.size();
		}
		
	}

	public List<T> getItems() {
		return this.items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public long getTotal_count() {
		return this.total_count;
	}
	public void setTotal_count(long total_count) {
		this.total_count = total_count;
	}
	public Boolean getInclomplete_results() {
		return this.inclomplete_results;
	}
	public void setInclomplete_results(Boolean inclomplete_results) {
		this.inclomplete_results = inclomplete_results;
	}
}
