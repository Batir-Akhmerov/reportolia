/**
 * 
 */
package com.reportolia.core.sql.query.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;

import com.reportolia.core.model.datatype.DataType;


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
	private DataType dataType;
	private int top;
	
	public void addTable(QueryTable table) {
		if (CollectionUtils.isEmpty(this.tableList)) {
			this.tableList = new ArrayList<>();
		}
		this.tableList.add(table);
	}
	
	public void addColumn(QueryColumn column) {
		if (CollectionUtils.isEmpty(this.columnList)) {
			this.columnList = new ArrayList<>();
		}
		this.columnList.add(column);
	}
	
	public QueryTable getMainTable() {
		if (CollectionUtils.isEmpty(this.tableList)) {
			return null;
		}
		Assert.isTrue(this.tableList.get(0).isMain(), "First table in the query must be a main table!");
		return this.tableList.get(0);
	}
	/*
	public boolean containsTableAlias(String alias) {
		return ListUtils.containsByProperty(this.tableList, "alias", alias);
	}
	
	public QueryTable findTableByAlias(String alias) {
		return ListUtils.findByProperty(this.tableList, "alias", alias);
	}
	*/
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

	public DataType getDataType() {
		return this.dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public int getTop() {
		return this.top;
	}

	public void setTop(int top) {
		this.top = top;
	}
	
	public boolean isTop1() {
		return this.top == 1; 
	}
	
	public void setTop1() {
		setTop(1);
	}

}
