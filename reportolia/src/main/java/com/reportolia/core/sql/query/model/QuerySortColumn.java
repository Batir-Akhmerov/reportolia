/**
 * 
 */
package com.reportolia.core.sql.query.model;

import com.reportolia.core.model.report.ReportColumn;

/**
 * The QuerySortColumn class
 *
 * @author Batir Akhmerov
 * Created on Jan 18, 2016
 */
public class QuerySortColumn {
	
	private int sortIndex;
	private boolean sortDesc;
	private int projectionIndex;
	
	private QueryColumn column;
	
	public QuerySortColumn(QueryColumn column, int sortIndex, int projectionIndex, boolean isDesc) {
		this.sortIndex = sortIndex;
		this.projectionIndex = projectionIndex;
		this.sortDesc = isDesc;
		this.column = column;
		
	}
	public QuerySortColumn(ReportColumn rColumn, QueryColumn column, int projectionIndex) {
		this.sortIndex = rColumn.getSort_index();
		this.projectionIndex = projectionIndex;
		this.sortDesc = rColumn.isSort_desc();
		this.column = column;
		
	}

	public int getSortIndex() {
		return this.sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public boolean isSortDesc() {
		return this.sortDesc;
	}

	public void setSortDesc(boolean sortDesc) {
		this.sortDesc = sortDesc;
	}

	public int getProjectionIndex() {
		return this.projectionIndex;
	}

	public void setProjectionIndex(int projectionIndex) {
		this.projectionIndex = projectionIndex;
	}

	public QueryColumn getColumn() {
		return this.column;
	}

	public void setColumn(QueryColumn column) {
		this.column = column;
	}

}
