package com.reportolia.core.repository.report;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.model.report.ReportColumnPath;
import com.reportolia.core.repository.base.UpdatableRepository;

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
