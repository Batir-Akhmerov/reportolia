package com.reportolia.core.repository.report;

import java.util.List;

import com.reportolia.core.model.folder.Folder;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.repository.base.UpdatableRepository;

/**
 * 
 * The ReportRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public interface ReportRepository extends UpdatableRepository<Report, Long> {
    
    List<Report> findByName(String name);
    List<Report> findByFolder(Folder folder);
}
