/**
 * 
 */
package com.reportolia.core.handler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.repository.report.ReportColumnPathRepository;
import com.reportolia.core.repository.report.ReportColumnRepository;

/**
 * The ReportManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
@Component
public class ReportManager implements ReportHandler {
	
	@Resource protected ReportColumnRepository reportColumnRepository;
	@Resource protected ReportColumnPathRepository reportColumnPathRepository;
	
	private Sort sortByOrder = new Sort(Sort.Direction.ASC, "order");

	public List<ReportColumn> getReportColumns(Long reportId) {
		return this.reportColumnRepository.findByReport(new Report(reportId), this.sortByOrder);
	}
	
	public List<ReportColumnPath> getReportColumnPaths(ReportColumn column) {
		return this.reportColumnPathRepository.findByReportColumn(column, this.sortByOrder);
	}
	
}
