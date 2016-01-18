/**
 * 
 */
package com.reportolia.core.sql.query.model;

import java.util.List;

import org.springframework.util.StringUtils;

import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.table.DbTableColumn;

/**
 * The QueryColumn class
 *
 * @author Batir Akhmerov
 * Created on Nov 26, 2015
 */
public class QueryColumn extends QueryOperand {
	
	private boolean hidden;
	private boolean excluded;
	private List<QueryOperand> operandList;
	
	public QueryColumn() {
		
	}
	
	public QueryColumn(DbTableColumn column, QueryTable table) {
		super(column, table);
	}
	
	public QueryColumn(List<QueryOperand> list) {
		this.operandList = list;
	}
	
	public QueryColumn(Query query) {
		super(query);
	}
	
	public QueryColumn(QueryOperand operand) {
		super(operand);
	}
	
	public void populateFromReportColumn(ReportColumn column) {
		setExcluded(column.isExcluded());
		setHidden(column.isHidden());
	}
	
	@Override
	public String toString() {
		String sql = super.toString();
		if (StringUtils.isEmpty(sql)) {
			sql = "OPERAND LIST";
		}
		return sql;
	}
	
	
	public List<QueryOperand> getOperandList() {
		return this.operandList;
	}
	public void setOperandList(List<QueryOperand> operandList) {
		this.operandList = operandList;
	}

	public boolean isHidden() {
		return this.hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isExcluded() {
		return this.excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}
	
}
