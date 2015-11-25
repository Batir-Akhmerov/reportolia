/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.List;

/**
 * The QueryOperand class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public class QueryOperand {
	
	private QueryTable table;
	private String sql;
	private List<Object> valueList;

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public QueryTable getTable() {
		return this.table;
	}

	public void setTable(QueryTable table) {
		this.table = table;
	}

	public List<Object> getValueList() {
		return this.valueList;
	}

	public void setValueList(List<Object> valueList) {
		this.valueList = valueList;
	}

}
