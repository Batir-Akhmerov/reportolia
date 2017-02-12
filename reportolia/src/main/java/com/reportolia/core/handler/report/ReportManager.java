/**
 * 
 */
package com.reportolia.core.handler.report;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.reportolia.core.model.folder.Folder;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.repository.folder.FolderRepository;
import com.reportolia.core.repository.report.ReportColumnPathRepository;
import com.reportolia.core.repository.report.ReportColumnRepository;
import com.reportolia.core.repository.report.ReportRepository;
import com.reportolia.core.utils.CoreUtils;
import com.reportolia.core.web.controllers.report.ReportListJsonForm;

/**
 * The ReportManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
@Service
public class ReportManager  {
	
	@Resource protected ReportRepository reportRepository;
	@Resource protected ReportColumnRepository reportColumnRepository;
	@Resource protected ReportColumnPathRepository reportColumnPathRepository;
	
	@Resource protected FolderRepository folderRepository;
	
	public static Sort sortByOrder = new Sort(Sort.Direction.ASC, "order");	
	public static Sort folderSortByName = new Sort(Sort.Direction.ASC, "name");
	
	public static final String FOLDER_SYSTEM_REPORTS = "Reports";
	
	public List<Report> getReportList(ReportListJsonForm form) {
		Folder folder = null;
		if (CoreUtils.isKeyNull(form.getFolderId())) {
			folder = this.folderRepository.findOneBySystemName(FOLDER_SYSTEM_REPORTS);
		}
		folder = this.folderRepository.findById(form.getFolderId());
		return this.reportRepository.findByFolder(folder, folderSortByName);
	}
	
	public void deleteReport(long reportId) {
		this.reportRepository.delete(reportId);
	}


	/*************************************************/
	/** COLUMNS **************************************/
	/*************************************************/
	
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
