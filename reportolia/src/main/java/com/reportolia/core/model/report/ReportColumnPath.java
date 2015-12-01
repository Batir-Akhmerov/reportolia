package com.reportolia.core.model.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.model.table.DbTableRelationship;

/**
 * 
 * The ReportColumnPath class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@Entity
@Table(name="r3p_report_column_path")
public class ReportColumnPath extends BaseEntity {
    
	
	@NotEmpty
	@ManyToOne
    @JoinColumn(name="report_column_id", nullable=false)
    private ReportColumn reportColumn;
	
	@ManyToOne
    @JoinColumn(name="table_relatioship_id", nullable=false)
    private DbTableRelationship dbTableRelationship;
	
	@Column(name = "relationship_order")
	private int order;

	

	public DbTableRelationship getDbTableRelationship() {
		return this.dbTableRelationship;
	}

	public void setDbTableRelationship(DbTableRelationship dbTableRelationship) {
		this.dbTableRelationship = dbTableRelationship;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public ReportColumn getReportColumn() {
		return this.reportColumn;
	}

	public void setReportColumn(ReportColumn reportColumn) {
		this.reportColumn = reportColumn;
	}

	
}
