package com.reportolia.core.repository.report;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.reportolia.base.repository.UpdatableRepository;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;

/**
 * 
 * The ReportColumnPathRepository class
 *
 * @author Batir Akhmerov
 * Created on Dec 1, 2015
 */
public interface ReportColumnPathRepository extends UpdatableRepository<ReportColumnPath, Long> {
    
    List<ReportColumnPath> findByReportColumn(ReportColumn column, Sort sort);
     
}
