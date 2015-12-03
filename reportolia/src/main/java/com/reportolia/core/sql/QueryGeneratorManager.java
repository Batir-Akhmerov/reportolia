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
		 
		 List<ReportColumn> columnList = this.reportManager.getReportColumns(report);
		 if (CollectionUtils.isEmpty(columnList)) {
			 throw new ValidationException("Please add Columns to the Report!");
		 }
		 
		 for (ReportColumn column: columnList) {
			 // 1. Report column associated with table column directly
			 if (column.getDbTableColumn() != null) {
				 DbTableColumn tbColumn = column.getDbTableColumn();
				 
				 List<ReportColumnPath> columnPathList = this.reportManager.getReportColumnPaths(column);
				 QueryTable qColumnTable = appendTablesToQuery(query, columnPathList, command);
				 
				 QueryColumn qColumn = new QueryColumn(tbColumn, qColumnTable);
				 query.addColumn(qColumn);
			 }
		 }
		 
		 
		 return query;
	 }
	 
	 protected QueryTable appendTablesToQuery(Query query, List<ReportColumnPath> columnPathList, QueryGenerationCommand command) {
		 QueryTable qMainTable = query.getMainTable();
		 QueryTable qTable = qMainTable;
		 
		 if (!CollectionUtils.isEmpty(columnPathList)) { // no stamps means main query table is a column table 
			 return qTable;
		 }
		 
		 
		 StringBuilder stamp = new StringBuilder(QC.TBL_ALIAS + QC.UNDERSCORE);
		 
		 for (ReportColumnPath path: columnPathList) {
			 DbTableRelationship rel = path.getDbTableRelationship();
			 
			 DbTableColumn childColumn = rel.getDbColumnChild();
			 DbTable childTable = childColumn.getDbTable();
			 
			 DbTableColumn parentColumn = rel.getDbColumnParent();
			 DbTable parentTable = parentColumn.getDbTable();
			 
			 DbTable nextTable = parentTable; // assuming most table path follow from child to parent tables in the path
			 String fromChildMarker = "";
			 if (!path.isFromParent()) { // determine which table goes first in the path
				 Assert.isTrue(childTable.getName() == qTable.getTableName());
				 nextTable = childTable;
				 fromChildMarker = QC.MARKER_PATH_FROM_CHILD;
			 }
			 else {
				 Assert.isTrue(parentTable.getName() == qTable.getTableName());
			 }
			 
			 String alias = stamp.toString() + fromChildMarker + rel.getId();
			 
			 qTable = query.findTableByAlias(alias);
			 if (qTable == null) {
				 qTable = new QueryTable(nextTable, alias);
				 query.addTable(qTable);
				 
				 // update stamp
				 if (!StringUtils.isEmpty(fromChildMarker)) {
					 stamp.append(fromChildMarker);
				 }
				 stamp.append(rel.getId());
				 stamp.append(QC.UNDERSCORE);
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
