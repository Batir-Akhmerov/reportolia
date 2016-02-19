package com.reportolia.core.model.report;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.reportolia.core.model.base.BaseColumnPath;

/**
 * 
 * The ReportColumnPath class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@Entity
@Table(name="r3p_report_column_path")
public class ReportColumnPath extends BaseColumnPath {
    
	
	@ManyToOne
    @JoinColumn(name="report_column_id", nullable=false)
    private ReportColumn reportColumn;

	public ReportColumn getReportColumn() {
		return this.reportColumn;
	}

	public void setReportColumn(ReportColumn reportColumn) {
		this.reportColumn = reportColumn;
	}
	
		
}
