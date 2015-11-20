package com.reportolia.core.model.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.StringUtils;

import com.reportolia.core.model.base.BaseEntity;

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
    
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    
    @Column(name = "label")
    private String label;
   
	
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
	
	
}
