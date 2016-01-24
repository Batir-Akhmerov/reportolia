package com.reportolia.core.model.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.StringUtils;

import com.reportolia.core.Constants;
import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.model.datatype.DataType;

/**
 * 
 * The DbTableColumn class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_table_columns", uniqueConstraints = { @UniqueConstraint(columnNames = {"table_id", "name"}) })
public class DbTableColumn extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name="table_id", nullable=false)
    private DbTable dbTable;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "data_type", nullable = false, length = Constants.LENGTH_DATA_TYPE)
    private DataType dataType;
    
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    
    @Column(name = "label")
    private String label;
    
    @Column(name = "is_calculated", columnDefinition = "boolean default false")
    private Boolean calculated;
    
    @Column(name = "is_not_correlated", columnDefinition = "boolean default false")
    private Boolean notCorrelated;
    
    @Column(name = "is_pk", columnDefinition = "boolean default false")
    private Boolean pk;
   
    @Override
   	public String toString() {
   		return this.name;
   	}
    
	/**
	 * Optional column label
	 * @return
	 */
	public String getLabel() {
		return StringUtils.isEmpty(this.label) ? this.name : this.label;
	}


	public DbTable getDbTable() {
		return this.dbTable;
	}


	public void setDbTable(DbTable dbTable) {
		this.dbTable = dbTable;
	}


	public String getName() {
		return this.name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public DataType getDataType() {
		return this.dataType;
	}


	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}


	public Boolean isCalculated() {
		return this.calculated;
	}


	public void setCalculated(Boolean calculated) {
		this.calculated = calculated != null ? calculated : false;
	}

	public Boolean isPk() {
		return this.pk;
	}
	
	public Boolean isNotCorrelated() {
		return this.notCorrelated != null ? this.notCorrelated : false;
	}

	public void setNotCorrelated(Boolean notCorrelated) {
		this.notCorrelated = notCorrelated;
	}
	
	
}
