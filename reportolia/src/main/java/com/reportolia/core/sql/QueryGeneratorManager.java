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
import com.reportolia.core.handler.ReportHandler;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.repository.report.ReportColumnRepository;
import com.reportolia.core.sql.query.QC;
import com.reportolia.core.sql.query.Query;
import com.reportolia.core.sql.query.QueryColumn;
import com.reportolia.core.sql.query.QueryTable;

/**
 * The QueryGeneratorManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Component
public class QueryGeneratorManager implements QueryGeneratorHandler {
	 
	 @Resource protected DbHandler dbManager;
	 @Resource protected ReportHandler reportManager;
	 @Resource protected ReportColumnRepository reportColumnRepository;
	 
	 public Query getReportQuery(Report report) {
		 Query query = new Query();
		 QueryGenerationCommand command = new QueryGenerationCommand();
		 
		 DbTable reportTable = report.getDbTable();
		 QueryTable qTable = new QueryTable(reportTable, command, true);
		 query.addTable(qTable);
		 
		 List<ReportColumn> columnList = this.reportManager.getReportColumns(report.getId());
		 if (CollectionUtils.isEmpty(columnList)) {
			 throw new ValidationException("Please add Columns to the Report!");
		 }
		 
		 for (ReportColumn column: columnList) {
			 // report column associated with table column directly
			 if (column.getDbTableColumn() != null) {
				 DbTableColumn tbColumn = column.getDbTableColumn();
				 QueryTable qColumnTable =  qTable;
			 
				 List<ReportColumnPath> columnPathList = this.reportManager.getReportColumnPaths(column);
				 if (!CollectionUtils.isEmpty(columnPathList)) {
					 
				 }
				 
				 QueryColumn qColumn = new QueryColumn(tbColumn, qColumnTable);
				 query.addColumn(qColumn);
			 }
		 }
		 
		 
		 return query;
	 }
	 
	 protected QueryTable appendTablesToQuery(Query query, List<ReportColumnPath> columnPathList, QueryGenerationCommand command) {
		 QueryTable qMainTable = query.getMainTable();
		 QueryTable qTable = null;
		 StringBuilder stamp = new StringBuilder(QC.TBL_ALIAS + QC.UNDERSCORE);
		 
		 for (ReportColumnPath path: columnPathList) {
			 DbTableRelationship rel = path.getDbTableRelationship();
			 stamp.append(rel.getId());
			 
			 DbTableColumn childColumn = rel.getDbColumnChild();
			 DbTable childTable = childColumn.getDbTable();
			 
			 DbTableColumn parentColumn = rel.getDbColumnParent();
			 DbTable parentTable = parentColumn.getDbTable();
			 
			 // first iteration, find out which one of parent/child tables is a main table
			 if (qTable == null) {
				 
				 // TODO: How to track circular relationships???
				 
				 DbTable tb = parentTable;
				 if (qMainTable.getTableName() == childTable.getName()) {
					 tb = childTable;
				 }
				 //else if (qMainTable.getTableName() == parentTable.getName()) {
					 
				 //}
				 
				 qTable = new QueryTable(tb, stamp.toString());
				 query.addTable(qTable);
			 }
			 else {
				 
			 }
			 
		 }
		 return qTable;
	 }
	 
	 
	 
	 
	 
	 public List<DbTable> getDbTableList(String name) {
		 List<DbTable> list = this.dbManager.getTableList(name);
		 return list;
	 }
	 
	 public List<DbTableRelationship> getDbTableChildRelationshipList(long  tableId) {
		 return this.dbManager.getTableChildRelationshipList(tableId);
	 }
}
