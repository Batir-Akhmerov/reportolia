/**
 * 
 */
package com.reportolia.core.sql;

import java.util.List;

import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.sql.query.Query;

/**
 * The QueryGeneratorHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface QueryGeneratorHandler {
	
	public Query getReportQuery(Report report);
	
	
	public List<DbTable> getDbTableList(String name);
	
	public List<DbTableRelationship> getDbTableChildRelationshipList(long tableId);

}
