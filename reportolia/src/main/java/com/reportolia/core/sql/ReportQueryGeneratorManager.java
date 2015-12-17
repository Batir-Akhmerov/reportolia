/**
 * 
 */
package com.reportolia.core.sql;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.reportolia.core.handler.DbHandler;
import com.reportolia.core.handler.operand.OperandHandler;
import com.reportolia.core.handler.report.ReportHandler;
import com.reportolia.core.model.operand.Operand;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.repository.report.ReportColumnRepository;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.sql.query.QueryGenerationCommand;
import com.reportolia.core.sql.query.QueryGeneratorHandler;
import com.reportolia.core.sql.query.model.Query;
import com.reportolia.core.sql.query.model.QueryColumn;
import com.reportolia.core.sql.query.model.QueryTable;

/**
 * The QueryGeneratorManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Component
public class ReportQueryGeneratorManager implements ReportQueryGeneratorHandler {
	 
	@Resource protected DbHandler dbManager;
	@Resource protected QueryGeneratorHandler columnQueryGeneratorHandler;
	@Resource protected OperandHandler reportStaticFilterOperandHandler;
	
	@Resource protected ReportHandler reportManager;
	@Resource protected ReportColumnRepository reportColumnRepository;
	@Resource protected DbTableRelationshipRepository tableRelationshipRepository;
	 
	public Query getReportQuery(Report report) {
		Query query = new Query();
		QueryGenerationCommand command = new QueryGenerationCommand();
		 
		DbTable reportTable = report.getDbTable();
		QueryTable qTable = new QueryTable(reportTable, command, true);
		query.addTable(qTable);
		 
		List<ReportColumn> columnList = this.reportManager.getReportColumns(report);
		if (CollectionUtils.isEmpty(columnList)) {
			throw new ValidationException("Please add Columns to the Report!");
		}
		 
		for (ReportColumn column: columnList) {
			// 1. Report column associated with table column directly
			if (column.getDbTableColumn() != null) {
				DbTableColumn tbColumn = column.getDbTableColumn();
				 
				List<ReportColumnPath> columnPathList = this.reportManager.getReportColumnPaths(column);
				QueryTable qColumnTable = this.columnQueryGeneratorHandler.appendTablesToQuery(query, columnPathList, command);
				
				QueryColumn qColumn = null;
				
				if (tbColumn.isCalculated() == null || !tbColumn.isCalculated()) {
					qColumn = new QueryColumn(tbColumn, qColumnTable);
				}
				else {
					QueryGenerationCommand nestedCommand = new QueryGenerationCommand();
					nestedCommand.setMainTable(qColumnTable.getTable());
					Query nestedQuery = this.columnQueryGeneratorHandler.getQuery(column.getDbTableColumn().getId(), qColumnTable, nestedCommand);
					qColumn = new QueryColumn(nestedQuery);
				}
				query.addColumn(qColumn);
			}
		}
		
		List<Operand> filterList = this.reportStaticFilterOperandHandler.getOperandsByOwner(report.getId());
		if (!CollectionUtils.isEmpty(filterList)) { 
			query.setFilterList(this.columnQueryGeneratorHandler.createQueryOperands(query, filterList, command));
		}
		 
		return query;
	}

	 
}
