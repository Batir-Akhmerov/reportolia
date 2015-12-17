/**
 * 
 */
package com.reportolia.core.handler.report;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.repository.report.ReportColumnPathRepository;
import com.reportolia.core.repository.report.ReportColumnRepository;
import com.reportolia.core.repository.report.ReportRepository;

/**
 * The ReportManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
@Component
public class ReportManager implements ReportHandler {
	
	@Resource protected ReportRepository reportRepository;
	@Resource protected ReportColumnRepository reportColumnRepository;
	@Resource protected ReportColumnPathRepository reportColumnPathRepository;
	
	public static Sort sortByOrder = new Sort(Sort.Direction.ASC, "order");

	public List<ReportColumn> getReportColumns(Long reportId) {
		//Report report = this.reportRepository.findById(reportId);
		//return this.reportColumnRepository.findByReport(report, sortByOrder);
		return this.reportColumnRepository.findByReportId(reportId, sortByOrder);
	}
	
	public List<ReportColumn> getReportColumns(Report report) {
		//return this.reportColumnRepository.findByReport(report, sortByOrder);
		return this.reportColumnRepository.findByReportId(report.getId(), sortByOrder);
	}
	
	public List<ReportColumnPath> getReportColumnPaths(ReportColumn column) {
		return this.reportColumnPathRepository.findByReportColumn(column, sortByOrder);
	}
	
}
