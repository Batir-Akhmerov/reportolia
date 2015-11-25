package com.reportolia.core.repository.report;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.repository.base.UpdatableRepository;

/**
 * 
 * The ReportColumnRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public interface ReportColumnRepository extends UpdatableRepository<ReportColumn, Long> {
    
    List<ReportColumn> findByReport(Report report, Sort sort);
    
}
