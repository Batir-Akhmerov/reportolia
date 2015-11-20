package com.reportolia.core.model;

/**
 * 
 * The DatabaseType class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public enum DatabaseType {	
	BASE("Common Database Type");
	
	private String description;
	
	DatabaseType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
    
}
