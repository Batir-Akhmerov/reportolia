package com.reportolia.core.model.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.reportolia.core.Constants;
import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.model.datatype.DataType;
import com.reportolia.core.model.table.DbTableColumn;

/**
 * 
 * The ReportColumn class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
@Entity
@Table(name="r3p_report_columns", uniqueConstraints = { @UniqueConstraint(columnNames = {"report_id", "name"}) })
public class ReportColumn extends BaseEntity {
    
	
	@ManyToOne
    @JoinColumn(name="report_id", nullable=false)
    private Report report;
		
	@ManyToOne
    @JoinColumn(name="table_column_id")
    private DbTableColumn dbTableColumn;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "data_type", nullable = false, length = Constants.LENGTH_DATA_TYPE)
    private DataType dataType;
    
    
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "column_order")
    private Integer order;
    
    @Column(name = "sort_index")
    private Integer sort_index;
    
    @Column(name = "sort_desc", columnDefinition = "boolean default false")
    private Boolean sort_desc;
    
   
	public String getName() {
		return this.name;
	}


	public void setName(String name) {
		this.name = name;
	}




	public String getDescription() {
		return this.description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public DbTableColumn getDbTableColumn() {
		return this.dbTableColumn;
	}


	public void setDbTableColumn(DbTableColumn dbTableColumn) {
		this.dbTableColumn = dbTableColumn;
	}


	public Integer getOrder() {
		return this.order;
	}


	public void setOrder(Integer order) {
		this.order = order;
	}


	public Integer getSort_index() {
		return this.sort_index;
	}


	public void setSort_index(Integer sort_index) {
		this.sort_index = sort_index;
	}


	public Report getReport() {
		return this.report;
	}


	public void setReport(Report report) {
		this.report = report;
	}


	public Boolean isSort_desc() {
		return this.sort_desc;
	}


	public void setSort_desc(Boolean sort_desc) {
		this.sort_desc = sort_desc != null ? sort_desc : false;
	}


	public DataType getDataType() {
		return this.dataType;
	}


	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}


	
}
