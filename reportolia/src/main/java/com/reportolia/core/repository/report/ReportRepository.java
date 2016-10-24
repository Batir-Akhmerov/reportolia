package com.reportolia.core.repository.report;

import java.util.List;

import com.reportolia.base.repository.UpdatableRepository;
import com.reportolia.core.model.report.Report;

/**
 * 
 * The ReportRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public interface ReportRepository extends UpdatableRepository<Report, Long> {
    
    List<Report> findByName(String name);
    //List<Report> findByFolder(Folder folder);
}
