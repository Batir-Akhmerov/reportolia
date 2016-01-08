/**
 * 
 */
package com.reportolia.core.sql.query.model;

/**
 * The JoinType class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public enum SecurityType {
	FULL("Apply security filters to Main and Nested Queries"),
	MAIN("Apply security filters to Main Query only"),
	NONE("No Security");
	
	String description;
	
	SecurityType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	

}
