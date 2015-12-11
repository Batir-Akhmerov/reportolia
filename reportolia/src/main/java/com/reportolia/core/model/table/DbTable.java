package com.reportolia.core.model.table;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.reportolia.core.model.base.BaseEntity;

/**
 * 
 * The DbTable class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_tables", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class DbTable extends BaseEntity {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 128;
    
    @NotEmpty
    @Column(name = "name", nullable = false, length = MAX_LENGTH_NAME)
    private String name;
    
    @Column(name = "label", nullable = true, length = MAX_LENGTH_NAME)
    private String label;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    @OneToMany(targetEntity=DbTableColumn.class, mappedBy="dbTable", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DbTableColumn> dbTableColumns;

    @Override
	public String toString() {
		return this.name;
	}
    
    /**
     * System Table Name
     * @return
     */
    public String getName() {
        return this.name;
    }
    
    /**
	 * Optional Table Label
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}
    
    /**
     * Table Description
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * List of table columns
     * @return
     */
    public List<DbTableColumn> getDbTableColumns() {
    	return this.dbTableColumns;
    }

	public void setName(String name) {
		this.name = name;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDbTableColumns(List<DbTableColumn> dbTableColumns) {
		this.dbTableColumns = dbTableColumns;
	}
	
    
}
