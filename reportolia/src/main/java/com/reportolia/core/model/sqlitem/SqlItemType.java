package com.reportolia.core.model.sqlitem;

/**
 * 
 * The SqlItemType enum
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public enum SqlItemType {
	LOGIC("Logic Operators"),
	OPERATOR("Arithmetical Operators and Parenthesis"),
	COMPARATOR("Comparators"),
	FUNCTION("Functions");
	
	private String description;
	
	SqlItemType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	
}
