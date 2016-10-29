/**
 * 
 */
package com.reportolia.core.sql;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.handler.operand.OperandHandler;
import com.reportolia.core.handler.report.ReportHandler;
import com.reportolia.core.model.operand.Operand;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.variable.VariableValueConsumer;
import com.reportolia.core.repository.report.ReportColumnRepository;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.sql.query.QueryGenerationCommand;
import com.reportolia.core.sql.query.QueryGeneratorHandler;
import com.reportolia.core.sql.query.model.Query;
import com.reportolia.core.sql.query.model.QueryColumn;
import com.reportolia.core.sql.query.model.QueryTable;
import com.reportolia.core.utils.ListUtils;

/**
 * The QueryGeneratorManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Component
public class ReportQueryGeneratorManager implements ReportQueryGeneratorHandler {
	 
	@Resource protected DbHandler dbHandler;
	@Resource protected QueryGeneratorHandler columnQueryGeneratorHandler;
	
	@Resource protected OperandHandler reportColumnOperandHandler;
	@Resource protected OperandHandler reportStaticFilterOperandHandler;
	
	@Resource protected ReportHandler reportManager;
	@Resource protected ReportColumnRepository reportColumnRepository;
	@Resource protected DbTableRelationshipRepository tableRelationshipRepository;
	
	public Query getReportQuery(Report report) {
		Query query = new Query();
		return getReportQuery(report, query);
	}
	 
	public Query getReportQuery(Report report, Query query) {
		QueryGenerationCommand command = new QueryGenerationCommand();
		 
		DbTable reportTable = report.getDbTable();
		QueryTable qTable = new QueryTable(reportTable, command, true);
		query.addTable(qTable);
		if (query.isSecured()) {
			if (reportTable.isSecurityFilter()){
				Assert.isTrue(reportTable.isSecurityFilterSql(), "Main query table ["+reportTable.getName()+"] cannot be a security filter of a table type!");
				this.columnQueryGeneratorHandler.addSqlFilterToOwner(reportTable, this.columnQueryGeneratorHandler.getSecurityFilterAlias(reportTable, null), qTable, query, null, command);
			}
			else {
				this.columnQueryGeneratorHandler.appendFilterByQueryTable(query, qTable, this.columnQueryGeneratorHandler.getBeginningAlias(qTable), null, command);
			}
		}
		 
		List<ReportColumn> columnList = this.reportManager.getReportColumns(report);
		if (CollectionUtils.isEmpty(columnList)) {
			throw new ValidationException("Please add Columns to the Report!");
		}
		
		boolean isAggregated = ListUtils.containsByProperty(columnList, "aggregated", true); // TODO: column.aggregated must be set during column content save!!!
		query.setAggregated(isAggregated);
		
	// SELECT: column projection
		int projectionIndex = 1;
		for (ReportColumn column: columnList) {
			QueryColumn qColumn = null;
			
			// 1. Report column associated with table column directly
			if (column.getDbTableColumn() != null) {
				DbTableColumn tbColumn = column.getDbTableColumn();
				 
				List<ReportColumnPath> columnPathList = this.reportManager.getReportColumnPaths(column);
				QueryTable qColumnTable = this.columnQueryGeneratorHandler.appendTablesToQuery(query, columnPathList, command);
				
				if (!tbColumn.isCalculated()) {
					qColumn = new QueryColumn(tbColumn, qColumnTable);
					if (query.isAggregated()) {
						command.addGroupByOperand(qColumn);
					}
				}
				else {
					QueryGenerationCommand nestedCommand = new QueryGenerationCommand(query, qColumnTable, column.getDbTableColumn(), command);		
					// variable consumer is Report Column
					nestedCommand.setVariableValueConsumer(VariableValueConsumer.newConsumerReportColumn(column.getId()));
					
					Query nestedQuery = this.columnQueryGeneratorHandler.getQuery(column.getDbTableColumn().getId(), nestedCommand);
					qColumn = new QueryColumn(nestedQuery);
				}
			}
			// 2. Report Level Calculated Column
			else {
				List<Operand> contentList = this.reportColumnOperandHandler.getOperandsByOwner(column.getId());
				if (CollectionUtils.isEmpty(contentList)) { 
					throw new ValidationException(String.format("No expression can be found in Calculated Report Column [%s]!", column.getName()));
				}
				qColumn = new QueryColumn(this.columnQueryGeneratorHandler.createQueryOperands(query, contentList, command));
			}
			
			query.addColumn(qColumn);
			
			qColumn.populateFromReportColumn(column);
			
			if (column.isSorted()) {
				// add to ORDER BY clause
				query.addSortColumn(column, qColumn, projectionIndex);				
			}
			
			if (column.isExcluded()) {
				projectionIndex++;
			}
			
		}
	// WHERE clause	
		List<Operand> filterList = this.reportStaticFilterOperandHandler.getOperandsByOwner(report.getId());
		if (!CollectionUtils.isEmpty(filterList)) { 
			query.setFilterList(this.columnQueryGeneratorHandler.createQueryOperands(query, filterList, command));
		}
		
		// GROUP BY clause
		if (!CollectionUtils.isEmpty(command.getGroupByList())) {
			 query.setGroupList(command.getGroupByList());
		}
		
		 
		return query;
	}

	 
}
