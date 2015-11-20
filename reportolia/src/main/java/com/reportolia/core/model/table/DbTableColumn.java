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
    
    @Column(name = "is_calculated")
    private boolean calculated;
   
	
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


	/**
	 *
	 * @return the dataType
	 */
	public DataType getDataType() {
		return this.dataType;
	}


	/**
	 *
	 * @param dataType the dataType to set
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}


	/**
	 *
	 * @return the calculated
	 */
	public boolean isCalculated() {
		return this.calculated;
	}


	/**
	 *
	 * @param calculated the calculated to set
	 */
	public void setCalculated(boolean calculated) {
		this.calculated = calculated;
	}
	
	
}
