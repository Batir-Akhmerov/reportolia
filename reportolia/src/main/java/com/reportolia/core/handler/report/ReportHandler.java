/**
 * 
 */
package com.reportolia.core.handler.report;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.web.controllers.report.ReportListJsonForm;

/**
 * The ReportHandler class
 *
 * @author Batir Akhmerov
 * Created on Dec 18, 2017
 */
public interface ReportHandler  {
		
	public static Sort sortByOrder = new Sort(Sort.Direction.ASC, "order");	
	public static Sort folderSortByName = new Sort(Sort.Direction.ASC, "name");
	
	public static final String FOLDER_SYSTEM_REPORTS = "Reports";
	
	public List<Report> getReportList(ReportListJsonForm form);
	
	public Report saveReport(Report report);
	
	public void deleteReport(long reportId);


	/*************************************************/
	/** COLUMNS **************************************/
	/*************************************************/
	
	public List<ReportColumn> getReportColumns(Long reportId);
	
	public List<ReportColumn> getReportColumns(Report report);
	
	public List<ReportColumnPath> getReportColumnPaths(ReportColumn column);
	
}
