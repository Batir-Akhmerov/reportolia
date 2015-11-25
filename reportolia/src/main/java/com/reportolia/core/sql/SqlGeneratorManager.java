/**
 * 
 */
package com.reportolia.core.sql;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.reportolia.core.handler.DbHandler;
import com.reportolia.core.handler.ReportHandler;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.sql.query.Query;

/**
 * The DbManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Component
public class SqlGeneratorManager implements SqlGeneratorHandler {
	 
	 @Resource protected DbHandler dbManager;
	 @Resource protected ReportHandler reportManager;
	 
	 public Query getReportQuery(Report report) {
		 Query query = new Query();
		 
		 return query;
	 }
	 
	 public List<DbTable> getDbTableList(String name) {
		 List<DbTable> list = this.dbManager.getTableList(name);
		 return list;
	 }
	 
	 public List<DbTableRelationship> getDbTableChildRelationshipList(long  tableId) {
		 return this.dbManager.getTableChildRelationshipList(tableId);
	 }
}
