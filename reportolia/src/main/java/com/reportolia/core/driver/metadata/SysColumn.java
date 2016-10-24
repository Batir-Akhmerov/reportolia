/**
 * 
 */
package com.reportolia.core.driver.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The SysColumn class
 *
 * @author Batir Akhmerov
 * Created on Oct 13, 2016
 */
public class SysColumn {
	
	public static final String COL_NAME = "COLUMN_NAME";
	public static final String COL_TYPE_NAME = "TYPE_NAME";
	public static final String COL_SIZE = "COLUMN_SIZE";
	
	@JsonProperty("tableName")
	private String tableName;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("size")
	private String size;
	
	@JsonProperty("selected")
	private boolean selected;
	
	@JsonProperty("isColumn")
	private boolean column = true;
	
	
	public SysColumn() {
		//
	}
	
	public SysColumn(String tableName, String name, String type, String size) {
		this.tableName = tableName;
		this.name = name;
		this.type = type;
		this.size = size;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
