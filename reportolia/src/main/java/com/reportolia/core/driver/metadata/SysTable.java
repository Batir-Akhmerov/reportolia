/**
 * 
 */
package com.reportolia.core.driver.metadata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The SysTable class
 *
 * @author Batir Akhmerov
 * Created on Oct 13, 2016
 */
public class SysTable {
	
	public static final String COL_TABLE_NAME = "TABLE_NAME";
	public static final String COL_TYPE_NAME = "TABLE_TYPE";
	public static final String COL_SCHEMA_NAME = "TABLE_SCHEM";
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("schema")
	private String schema;
	
	@JsonProperty("selected")
	private boolean selected;
	
	@JsonProperty("columnList")
	private List<SysColumn> columnList;
	
	public SysTable() {
		//
	}
	
	public SysTable(String name, String type, String schema) {
		this.name = name;
		this.type = type;
		this.schema = schema;
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
	public String getSchema() {
		return this.schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}

	public List<SysColumn> getColumnList() {
		return this.columnList;
	}

	public void setColumnList(List<SysColumn> columnList) {
		this.columnList = columnList;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
