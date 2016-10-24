package com.reportolia.core.repository.report;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.reportolia.base.repository.UpdatableRepository;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;

/**
 * 
 * The ReportColumnRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public interface ReportColumnRepository extends UpdatableRepository<ReportColumn, Long> {
    
    List<ReportColumn> findByReport(Report report, Sort sort); 
    
    List<ReportColumn> findByReportId(Long reportId, Sort sort);
    
}
