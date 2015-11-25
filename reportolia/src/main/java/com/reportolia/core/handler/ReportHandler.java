/**
 * 
 */
package com.reportolia.core.handler;

import java.util.List;

import com.reportolia.core.model.report.ReportColumn;

/**
 * The ReportHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public interface ReportHandler {
	
	public List<ReportColumn> getReportColumns(Long reportId);

}
