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
import org.springframework.util.StringUtils;

import com.reportolia.core.handler.DbHandler;
import com.reportolia.core.handler.ReportHandler;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.repository.report.ReportColumnRepository;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.sql.query.JoinType;
import com.reportolia.core.sql.query.QC;
import com.reportolia.core.sql.query.Query;
import com.reportolia.core.sql.query.QueryColumn;
import com.reportolia.core.sql.query.QueryJoin;
import com.reportolia.core.sql.query.QueryTable;

/**
 * The QueryGeneratorManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Component
public class ReportQueryGeneratorManager implements ReportQueryGeneratorHandler {
	 
	 @Resource protected DbHandler dbManager;
	 @Resource protected QueryGeneratorHandler queryGeneratorManager;
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
				 QueryTable qColumnTable = this.queryGeneratorManager.appendTablesToQuery(query, columnPathList, command);
				 
				 QueryColumn qColumn = new QueryColumn(tbColumn, qColumnTable);
				 query.addColumn(qColumn);
			 }
		 }
		 
		 
		 return query;
	 }

	 
}
