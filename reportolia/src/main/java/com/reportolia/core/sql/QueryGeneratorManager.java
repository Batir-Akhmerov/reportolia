/**
 * 
 */
package com.reportolia.core.sql;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.reportolia.core.handler.DbHandler;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
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
public class QueryGeneratorManager implements QueryGeneratorHandler {
	 
	 @Resource protected DbHandler dbManager;
	 @Resource protected DbTableRelationshipRepository tableRelationshipRepository;
	 
	 /*
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
	 */
	 //protected QueryTable appendOperandsToQuery(Query query, List<ReportColumnPath> columnPathList, QueryGenerationCommand command) {
	 
	 public QueryTable appendTablesToQuery(Query query, List<ReportColumnPath> columnPathList, QueryGenerationCommand command) {
		 QueryTable qMainTable = query.getMainTable();
		 QueryTable qTable = qMainTable;
		 
		 if (CollectionUtils.isEmpty(columnPathList)) { // no stamps means main query table is a column table 
			 return qTable;
		 }
		 
		 StringBuilder stamp = new StringBuilder(qMainTable.getAlias() + QC.UNDERSCORE);
		 
		 for (ReportColumnPath path: columnPathList) {
			 DbTableRelationship rel = path.getDbTableRelationship();
			 
			 qTable = appendTableJoin(query, qTable, path, rel, stamp);
		 }
		 return qTable;
	 }
	 
	 protected QueryTable appendTableJoin(Query query, QueryTable qTable, ReportColumnPath path, DbTableRelationship rel, StringBuilder stamp) {
			 
		 DbTableColumn childColumn = rel.getDbColumnChild();
		 DbTable childTable = childColumn.getDbTable();
		 
		 DbTableColumn parentColumn = rel.getDbColumnParent();
		 DbTable parentTable = parentColumn.getDbTable();
		 
		 DbTable nextTable = parentTable; // assuming most table path follow from child to parent tables in the path
		 DbTableColumn nextColumn = parentColumn;
		 DbTableColumn prevColumn = childColumn;
		 
		 boolean isParentTableFirst = path.isFromParent();
		 
		 String fromChildMarker = "";
		 JoinType joinType = JoinType.INNER;
		 if (isParentTableFirst) { // determine which table goes first in the path
			 joinType = rel.getJoinTypeToChild();				 
			 Assert.isTrue(parentTable.getName() == qTable.getTableName());
			 nextTable = childTable;
			 nextColumn = childColumn;
			 prevColumn = parentColumn;
		 }
		 else {
			 Assert.isTrue(childTable.getName() == qTable.getTableName());				 
			 fromChildMarker = QC.MARKER_PATH_FROM_CHILD;
		 }
		 
		 String alias = stamp.toString() + fromChildMarker + rel.getId();
		 
		 QueryTable prevQTable = qTable;
		 qTable = query.findTableByAlias(alias);
		 if (qTable == null) {
			 qTable = new QueryTable(nextTable, alias);
			 qTable.setJoinType(joinType);
			 
			 appendQueryJoin(prevQTable, prevColumn, qTable, nextColumn, rel, isParentTableFirst);
			 
			 query.addTable(qTable);
			 
			 // update stamp
			 if (!StringUtils.isEmpty(fromChildMarker)) {
				 stamp.append(fromChildMarker);
			 }
			 stamp.append(rel.getId());
			 stamp.append(QC.UNDERSCORE);
		 }
		 return qTable;
	 }
	 
	 protected void appendQueryJoin(QueryTable prevQTable, DbTableColumn prevColumn, QueryTable nextQTable, DbTableColumn nextColumn, DbTableRelationship rel, boolean isParentTableFirst){
		 QueryJoin qJoin = createQueryJoin(prevQTable, prevColumn, nextQTable, nextColumn, rel);		 
		 nextQTable.addQueryJoin(qJoin);
		 
		 List<DbTableRelationship> groupRelList = rel.getDbTableRelationshipGroupList();
		 if (!CollectionUtils.isEmpty(groupRelList)) {
			 for (DbTableRelationship relChild: groupRelList) {
				 
				 DbTableColumn childColumn = relChild.getDbColumnChild();
				 DbTable childTable = childColumn != null ? childColumn.getDbTable() : null;
				 
				 DbTableColumn parentColumn = relChild.getDbColumnParent();
				 DbTable parentTable = parentColumn != null ? parentColumn.getDbTable() : null;
				 
				 if (isParentTableFirst) { // determine which table goes first in the path
					 Assert.isTrue(parentTable == null || parentTable.getName() == prevQTable.getTableName());
					 nextColumn = childColumn;
					 prevColumn = parentColumn;
				 }
				 else {
					 Assert.isTrue(childTable == null || childTable.getName() == nextQTable.getTableName());
					 nextColumn = parentColumn;
					 prevColumn = childColumn;
				 }
				 
				 qJoin = createQueryJoin(prevQTable, prevColumn, nextQTable, nextColumn, relChild);		 
				 nextQTable.addQueryJoin(qJoin);
			 }
		 }
	 }
	 
	 protected QueryJoin createQueryJoin(QueryTable prevQTable, DbTableColumn prevColumn, QueryTable nextQTable, DbTableColumn nextColumn, DbTableRelationship rel){
		 QueryJoin qJoin = new QueryJoin();
		 
		 if (StringUtils.isEmpty(rel.getJoinValue())) {
			 qJoin.setPkColumn(new QueryColumn(prevColumn, prevQTable));
			 qJoin.setJoinColumn(new QueryColumn(nextColumn, nextQTable));
		 }
		 else {
			 if (prevColumn != null) {
				 qJoin.setPkColumn(new QueryColumn(prevColumn, prevQTable));
			 }
			 else if (nextColumn != null) {
				 qJoin.setJoinColumn(new QueryColumn(nextColumn, nextQTable));
			 }
			 
			 String value = rel.getJoinValue();
			 //value = replaceTableAliasMarkers(value, prevQTable, nextQTable); // value is a '?' sql value in prepared statement
			 qJoin.setJoinValue(value);
		 }
		 return qJoin;
	 }
	 
	 protected String replaceTableAliasMarkers(String sql, QueryTable parentQTable, QueryTable childQTable){
		
		 if (StringUtils.isEmpty(sql)) {
			 return sql;
		 }
		 sql = sql.replace(QC.TBL_ALIAS_PARENT, parentQTable.getAlias());
		 sql = sql.replace(QC.TBL_ALIAS_CHILD, childQTable.getAlias());
			
		 return sql;
		
	 }
	 
}
