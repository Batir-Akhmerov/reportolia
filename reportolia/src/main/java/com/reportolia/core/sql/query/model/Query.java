/**
 * 
 */
package com.reportolia.core.sql.query.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;

import com.reportolia.core.model.datatype.DataType;
import com.reportolia.core.model.report.ReportColumn;


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
	private TreeSet<QuerySortColumn> sortingList;
	private List<QueryOperand> groupList;
	private DataType dataType;
	private int top;
	private SecurityType securityType = SecurityType.MAIN;
	private Query topQuery;
	private boolean aggregated;
	
	public Query(){
		
	}
	
	public Query(SecurityType securityType){
		this.securityType = securityType;
	}

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
	
	public void addSortColumn(ReportColumn column, QueryColumn qColumn, int projectionIndex) {
		this.getSortingList().add(new QuerySortColumn(column, qColumn, projectionIndex));
	}
	public void addSortColumn(QueryColumn qColumn, int sortIndex, boolean isDesc) {
		this.getSortingList().add(new QuerySortColumn(qColumn, sortIndex, sortIndex, isDesc));
	}
	
	public QueryTable getMainTable() {
		if (CollectionUtils.isEmpty(this.tableList)) {
			return null;
		}
		// !!! this is not the case when main query table belongs to the top query
		// Assert.isTrue(this.tableList.get(0).isMain(), "First table in the query must be a main table!");
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
	public TreeSet<QuerySortColumn> getSortingList() {
		if (this.sortingList == null) {
			this.sortingList = new TreeSet<>(new Comparator<QuerySortColumn>(){		
				@Override
				public int compare(QuerySortColumn o1, QuerySortColumn o2) {
					return o1.getSortIndex() - o2.getSortIndex();
				}
			});
		}
		return this.sortingList;
	}
	public void setSortingList(TreeSet<QuerySortColumn> sortingList) {
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

	public boolean isSecured() {
		SecurityType type = getSecurityType();
		return type == SecurityType.FULL || (type == SecurityType.MAIN && !isNested());
	}
	
	public SecurityType getSecurityType() {
		if (isNested()) {
			return getTopQuery().getSecurityType();
		}
		return this.securityType;
	}

	public void setSecurityType(SecurityType securityType) {
		if (isNested()) {
			return;
		}
		this.securityType = securityType;
	}

	public boolean isNested() {
		return this.topQuery != null;
	}

	public Query getTopQuery() {
		return this.topQuery;
	}

	public void setTopQuery(Query topQuery) {
		this.topQuery = topQuery;
	}

	public boolean isAggregated() {
		return this.aggregated;
	}

	public void setAggregated(boolean aggregated) {
		this.aggregated = aggregated;
	}

}
