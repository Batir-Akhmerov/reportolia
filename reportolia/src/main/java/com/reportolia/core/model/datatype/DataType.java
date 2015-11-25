/**
 * 
 */
package com.reportolia.core.model.datatype;

/**
 * The DataType enum
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
public enum DataType {
	TXT("Text"),
	NUM("Decimal Number"),
	INT("Integer Number"),
	DATE("Date"),
	BOOL("Boolean"),
	CURR("Currency");
	
	private String name;
	DataType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
