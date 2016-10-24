/**
 * 
 */
package com.reportolia.core.web.controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The JsonListForm class
 *
 * @author Batir Akhmerov
 * Created on Oct 14, 2016
 */
public class JsonListForm<T extends Object> {
	
	@JsonProperty("list")
	private List<T> list;
	@JsonProperty("removedList")
	private List<T> removedList;
	
	public List<T> getList() {
		return this.list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public List<T> getRemovedList() {
		return this.removedList;
	}
	public void setRemovedList(List<T> removedList) {
		this.removedList = removedList;
	}

}
