package com.reportolia.core.model.sqlitem;

/**
 * 
 * The SqlItemType enum
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public enum SqlItemType {
	LOGI("Logic Operators"),
	OPER("Arithmetical Operators and Parenthesis"),
	COMP("Comparators"),
	FUNC("Functions");
	
	private String description;
	
	SqlItemType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	
}
