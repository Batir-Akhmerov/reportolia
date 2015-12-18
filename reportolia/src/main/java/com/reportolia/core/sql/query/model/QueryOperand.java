/**
 * 
 */
package com.reportolia.core.sql.query.model;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.reportolia.core.model.datatype.DataType;
import com.reportolia.core.model.sqlitem.SqlItem;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.variable.Variable;

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
	private Query nestedQuery;
	private DataType dataType;
	private Variable variable;
	
	public QueryOperand() {
		//
	}
	
	public QueryOperand(String sql) {
		this.sql = sql;
	}
	
	public QueryOperand(DbTableColumn column, QueryTable table) {
		this.sql = column.getName();
		this.table = table;
		this.dataType = column.getDataType();
	}
	
	public QueryOperand(Query query) {
		this.nestedQuery = query;
		this.dataType = query.getDataType();
	}
	
	public QueryOperand(SqlItem item) {
		this.sql = item.getSql();
		this.dataType = item.getDataType();
	}
	public QueryOperand(Variable variable) {
		this.variable = variable;
		this.dataType = variable.getDataType();
	}
	@SuppressWarnings("unused")
	public QueryOperand(List<Object> valueList, DataType dataType) {
		this.valueList = valueList;
		this.dataType = dataType;
		if (!CollectionUtils.isEmpty(this.valueList)) {
			StringBuilder buff = new StringBuilder(QC.SPACE);
			boolean isFirst = true;
			for (Object v: this.valueList) {
				if (!isFirst) {
					buff.append(QC.COMMA);
				}
				buff.append(QC.Q);
				isFirst = false;
			}
			buff.append(QC.SPACE);
			this.sql = buff.toString();
		}
		
	}
	
	@Override
	public String toString() {
		if (this.table != null) {
			return this.table.getAlias() + QC.DOT + getSql();
		}
		return getSql();
	}

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

	public Query getNestedQuery() {
		return this.nestedQuery;
	}

	public void setNestedQuery(Query nestedQuery) {
		this.nestedQuery = nestedQuery;
	}

	public DataType getDataType() {
		return this.dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public Variable getVariable() {
		return this.variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

}
