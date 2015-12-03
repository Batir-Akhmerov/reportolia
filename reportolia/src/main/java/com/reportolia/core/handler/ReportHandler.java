/**
 * 
 */
package com.reportolia.core.handler;

import java.util.List;

import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;

/**
 * The ReportHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public interface ReportHandler {
	
	public List<ReportColumn> getReportColumns(Long reportId);
	
	public List<ReportColumn> getReportColumns(Report report);
	
	public List<ReportColumnPath> getReportColumnPaths(ReportColumn column);

}
