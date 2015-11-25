/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.List;

/**
 * The QueryColumn class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public class QueryColumn {
	
	private QueryTable table;
	private String sql;
	private List<QueryOperand> operandList;
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
