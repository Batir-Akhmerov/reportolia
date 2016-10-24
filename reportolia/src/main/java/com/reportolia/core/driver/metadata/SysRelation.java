/**
 * 
 */
package com.reportolia.core.driver.metadata;

/**
 * The SysRelation class
 *
 * @author Batir Akhmerov
 * Created on Oct 23, 2016
 */
public class SysRelation {
	
	public static final String PARENT_TABLE  = "PKTABLE_NAME"; // parent key table name
	public static final String PARENT_COLUMN = "PKCOLUMN_NAME"; // parent key column name
	public static final String CHILD_TABLE   = "FKTABLE_NAME"; // foreign key table name being exported
	public static final String CHILD_COLUMN  = "FKCOLUMN_NAME"; // foreign key column name
	
	//@JsonProperty("tableName")
	private String parentTableName;
	
	//@JsonProperty("columnName")
	private String parentColumnName;
	
	//@JsonProperty("tableName")
	private String childTableName;
	
	//@JsonProperty("columnName")
	private String childColumnName;
	
	
	
	public SysRelation() {
		//
	}
	
	public SysRelation(String parentTableName, String parentColumnName, String childTableName, String childColumnName) {
		this.parentTableName = parentTableName;
		this.parentColumnName = parentColumnName;
		this.childTableName = childTableName;
		this.childColumnName = childColumnName;
	}

	public String getParentTableName() {
		return this.parentTableName;
	}

	public void setParentTableName(String parentTableName) {
		this.parentTableName = parentTableName;
	}

	public String getParentColumnName() {
		return this.parentColumnName;
	}

	public void setParentColumnName(String parentColumnName) {
		this.parentColumnName = parentColumnName;
	}

	public String getChildTableName() {
		return this.childTableName;
	}

	public void setChildTableName(String childTableName) {
		this.childTableName = childTableName;
	}

	public String getChildColumnName() {
		return this.childColumnName;
	}

	public void setChildColumnName(String childColumnName) {
		this.childColumnName = childColumnName;
	}
	
}
