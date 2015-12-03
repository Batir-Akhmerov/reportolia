package com.reportolia.core.model.report;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.model.folder.Folder;
import com.reportolia.core.model.table.DbTable;

/**
 * 
 * The Report class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
@Entity
@Table(name="r3p_reports", uniqueConstraints = { @UniqueConstraint(columnNames = {"folder_id", "name"}) })
public class Report extends BaseEntity {
    
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="folder_id", nullable=false)
    private Folder folder;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="table_id", nullable=false)
    private DbTable dbTable;
    
    
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    
    @Column(name = "description", length = 500)
    private String description;
    
    public Report() {
    	
    }
    
    public Report(Long id) {
    	setId(id);
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


	public Folder getFolder() {
		return this.folder;
	}


	public void setFolder(Folder folder) {
		this.folder = folder;
	}


	public String getDescription() {
		return this.description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	
}
