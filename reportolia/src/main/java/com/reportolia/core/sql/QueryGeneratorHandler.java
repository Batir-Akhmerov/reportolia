/**
 * 
 */
package com.reportolia.core.sql;

import java.util.List;

import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.sql.query.Query;
import com.reportolia.core.sql.query.QueryTable;

/**
 * The QueryGeneratorHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface QueryGeneratorHandler {
	
	//public Query getReportQuery(Report report);
	
	public QueryTable appendTablesToQuery(Query query, List<ReportColumnPath> columnPathList, QueryGenerationCommand command);
	

}
