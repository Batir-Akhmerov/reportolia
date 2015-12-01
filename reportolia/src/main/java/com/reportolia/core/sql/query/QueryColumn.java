/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.List;

import com.reportolia.core.model.table.DbTableColumn;

/**
 * The QueryColumn class
 *
 * @author Batir Akhmerov
 * Created on Nov 26, 2015
 */
public class QueryColumn {
	
	private QueryTable table;
	private String sql;
	private List<QueryOperand> operandList;
	
	public QueryColumn() {
		
	}
	
	public QueryColumn(DbTableColumn column, QueryTable table) {
		this.sql = table.getAlias() + QC.DOT + column.getName();
	}
	
	public String getSql() {
		return this.sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<QueryOperand> getOperandList() {
		return this.operandList;
	}
	public void setOperandList(List<QueryOperand> operandList) {
		this.operandList = operandList;
	}
	public QueryTable getTable() {
		return this.table;
	}
	public void setTable(QueryTable table) {
		this.table = table;  
	}

}
