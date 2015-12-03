/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * The SqlGeneratorManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
@Component
public class SqlGeneratorManager implements SqlGeneratorHandler {
	
	
	
	public String toSql(Query query, List<Object> valueList) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(QC.SELECT);
		builder.append(QC.NL);
		builder.append(QC.TAB);
		
		// column projection
		toSqlColumns(query.getColumnList(), builder, valueList);
		
		//FROM clause
		builder.append(QC.NL);
		builder.append(QC.FROM);		
		toSqlTables(query.getTableList(), builder, valueList, false);
		
		// WHERE clause
		if (!CollectionUtils.isEmpty(query.getFilterList())) {
			builder.append(QC.NL);
			builder.append(QC.WHERE);
			builder.append(QC.NL);
			builder.append(QC.TAB);
			toSqlOperands(query.getFilterList(), builder, valueList);
		}
		
		// ORDER BY clause
		if (!CollectionUtils.isEmpty(query.getSortingList())) {
			builder.append(QC.NL);
			builder.append(QC.ORDER_BY);
			builder.append(QC.NL);
			builder.append(QC.TAB);
			toSqlOperands(query.getSortingList(), builder, valueList);
		}
		// GROUP BY clause
		if (!CollectionUtils.isEmpty(query.getSortingList())) {
			builder.append(QC.NL);
			builder.append(QC.GROUP_BY);
			builder.append(QC.NL);
			builder.append(QC.TAB);
			toSqlOperands(query.getSortingList(), builder, valueList);
		}
		
		return builder.toString();
	}
	
	/**
	 * TABLES
	 */
	protected void toSqlTables(List<QueryTable> tableList, StringBuilder builder, List<Object> valueList, boolean isMainFound) {
		if (CollectionUtils.isEmpty(tableList)){
			return;
		}
		for (QueryTable table: tableList) {
			if (table.isMain()) {
				builder.append(QC.SPACE);
				builder.append(table.getTableName());
				builder.append(QC.SPACE);
				builder.append(table.getAlias());
				builder.append(QC.NL);
				builder.append(QC.TAB);
				isMainFound = true;
			}
			else {
				Assert.isTrue(isMainFound, "Main QueryTable must be the first table in the list!");
				
				builder.append(table.getJoinType().getSql());
				builder.append(QC.SPACE);
				builder.append(table.getTableName());
				builder.append(QC.SPACE);
				builder.append(table.getAlias());
				builder.append(QC.ON);
				toSqlJoins(table, builder, valueList);
				builder.append(QC.NL);
				builder.append(QC.TAB);
			}
		}
	}
	
	protected void toSqlJoins(QueryTable table, StringBuilder builder, List<Object> valueList) {
		Assert.isTrue(!CollectionUtils.isEmpty(table.getJoinList()), "QueryTable.joinList cannot be null!");
		boolean isFirst = true;
		for (QueryJoin join: table.getJoinList()) {
			if (!isFirst) {
				builder.append(QC.SPACE);
				builder.append(QC.AND);
			}
			if (StringUtils.isEmpty(join.getJoinValue())) {
				builder.append(QC.SPACE);
				builder.append(table.getAlias());
				builder.append(QC.DOT);
				builder.append(join.getPkColumn().getSql());
				builder.append(QC.EG);
				builder.append(join.getJoinColumn().getTable().getAlias());
				builder.append(QC.DOT);
				builder.append(join.getJoinColumn().getSql());
				builder.append(QC.SPACE);
			}
			else {
				builder.append(QC.SPACE);
				if (join.getPkColumn() != null) {
					builder.append(table.getAlias());
					builder.append(QC.DOT);
					toSqlColumn(join.getPkColumn(), builder, valueList);
				}
				else if (join.getJoinColumn() != null) {
					builder.append(join.getJoinColumn().getTable().getAlias());
					builder.append(QC.DOT);
					toSqlColumn(join.getJoinColumn(), builder, valueList);
				}
				else {
					Assert.isTrue(false, "One of QueryJoin Columns is expected!");
				}
				builder.append(QC.EG);
				builder.append(QC.Q);
				valueList.add(join.getJoinValue());
				builder.append(QC.SPACE);
			}
			isFirst = false;
		}
	}
	
	
	protected void toSqlColumns(List<QueryColumn> columnList, StringBuilder builder, List<Object> valueList) {
		Assert.isTrue(!CollectionUtils.isEmpty(columnList), "Query.columnList cannot be empty");
		boolean isFirst = true;
		for (QueryColumn column: columnList) {
			builder.append(QC.NL);
			builder.append(QC.TAB);
			if (!isFirst) {
				builder.append(QC.COMMA);
			}
			toSqlColumn(column, builder, valueList);
			isFirst = false;
		}
	}
	
	protected void toSqlColumn(QueryColumn column, StringBuilder builder, List<Object> valueList) {
		if (!StringUtils.isEmpty(column.getSql())) {
			if (column.getTable() != null) {
				builder.append(column.getTable().getAlias());
				builder.append(QC.DOT);
			}
			builder.append(column.getSql());
		}
		else if (!CollectionUtils.isEmpty(column.getOperandList())) {
			toSqlOperands(column.getOperandList(), builder, valueList);
		}
	}
	
	protected void toSqlOperands(List<QueryOperand> operandList, StringBuilder builder, List<Object> valueList) {
		if (!CollectionUtils.isEmpty(operandList)) {
			return;
		}
		builder.append(QC.PL);
		for (QueryOperand operand: operandList) {
			toSqlOperand(operand, builder, valueList);
		}
		builder.append(QC.PR);
	}
	
	protected void toSqlOperand(QueryOperand operand, StringBuilder builder, List<Object> valueList) {
		Assert.isTrue(!StringUtils.isEmpty(operand.getSql()), "QueryOperand.sql cannot be null!");
		
		if (operand.getTable() != null) {
			builder.append(operand.getTable().getAlias());
			builder.append(QC.DOT);
		}
		builder.append(operand.getSql());
		if (!CollectionUtils.isEmpty(operand.getValueList())) {
			valueList.addAll(operand.getValueList());
		}
	}
	

}
