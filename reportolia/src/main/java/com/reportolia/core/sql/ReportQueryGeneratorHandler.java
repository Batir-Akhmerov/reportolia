/**
 * 
 */
package com.reportolia.core.sql;

import com.reportolia.core.model.report.Report;
import com.reportolia.core.sql.query.Query;

/**
 * The QueryGeneratorHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface ReportQueryGeneratorHandler {
	
	public Query getReportQuery(Report report);
	
}
