/**
 * 
 */
package com.reportolia.core.web.controllers;

import java.util.List;

/**
 * The JTableJsonResult class
 *
 * @author Batir Akhmerov
 * Created on Oct 17, 2016
 */
public class JTableJsonResult<T extends Object> {
	
	public static final String RESULT_OK = "OK";
	
	public String Result; // no getters and setters, sorry, had to
	public List<T> Records;
	
	public String Message;
	
	public JTableJsonResult() {
		this.Result = RESULT_OK;
	}
	
	public JTableJsonResult(List<T> list) {
		this.Result = RESULT_OK;
		this.Records = list;
	}
	public JTableJsonResult(String error) {
		this.Message = error;
	}
	/*
	public String getResult() {
		return this.Result;
	}
	public void setResult(String result) {
		this.Result = result;
	}
	public List<T> getRecords() {
		return this.Records;
	}
	public void setRecords(List<T> records) {
		this.Records = records;
	}

	public String getMessage() {
		return this.Message;
	}

	public void setMessage(String message) {
		this.Message = message;
	}
	*/
}
