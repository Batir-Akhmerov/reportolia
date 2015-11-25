/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.List;

/**
 * The Query class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public class Query {
	
	private List<QueryColumn> columnList;
	private List<QueryTable> tableList;
	private List<QueryOperand> filterList;
	private List<QueryOperand> sortingList;
	private List<QueryOperand> groupList;
	
	public List<QueryColumn> getColumnList() {
		return this.columnList;
	}
	public void setColumnList(List<QueryColumn> columnList) {
		this.columnList = columnList;
	}
	public List<QueryTable> getTableList() {
		return this.tableList;
	}
	public void setTableList(List<QueryTable> tableList) {
		this.tableList = tableList;
	}
	public List<QueryOperand> getFilterList() {
		return this.filterList;
	}
	public void setFilterList(List<QueryOperand> filterList) {
		this.filterList = filterList;
	}
	public List<QueryOperand> getSortingList() {
		return this.sortingList;
	}
	public void setSortingList(List<QueryOperand> sortingList) {
		this.sortingList = sortingList;
	}
	public List<QueryOperand> getGroupList() {
		return this.groupList;
	}
	public void setGroupList(List<QueryOperand> groupList) {
		this.groupList = groupList;
	}

}
