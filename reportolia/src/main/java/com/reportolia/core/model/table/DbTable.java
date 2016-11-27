package com.reportolia.core.model.table;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    
    @Column(name = "name", nullable = false, length = MAX_LENGTH_NAME)
    private String name;
    
    @Column(name = "label", nullable = true, length = MAX_LENGTH_NAME)
    private String label;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    @JsonIgnore
    @OneToMany(targetEntity=DbTableColumn.class, mappedBy="dbTable", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DbTableColumn> dbTableColumns;
    
    /**
     * If this property is true then security filter should be found for this table.
     * Security filter can be declared directly in this table's securityFilterSql field, 
     * or be found in closest parent tables by going through relationships having is_path_to_security_filter true.
     */
    @Column(name = "is_secured", columnDefinition = "boolean default false")
    private Boolean secured;
    
    /**
     * If this property is true then this table is a security filter. 
     * When securityFilterSql is blank the following filter will be generated using table composite relationships (if declared):
     * INNER JOIN SecurityMatrix <[{ALIAS_FILTER}]> ON <[{ALIAS_PARENT}]>.ID = <[{ALIAS_FILTER}]>.SecurityRowID AND <[{ALIAS_FILTER}]>.UserID = <[{USER_ID}]>
     */
    @Column(name = "is_security_filter", columnDefinition = "boolean default false")
    private Boolean securityFilter;
    
    /**
     * Optional row-level security filter for this table.
     * Should be declared as valid INNER JOIN script having special markers for table aliases
     * INNER JOIN SecurityMatrix <[{ALIAS_FILTER}]> ON <[{ALIAS_PARENT}]>.ID = <[{ALIAS_FILTER}]>.SecurityRowID AND <[{ALIAS_FILTER}]>.UserID = <[{USER_ID}]>  
     */
    @JsonIgnore
    @Column(name = "security_filter_sql", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String securityFilterSql;
    
    

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
		if (StringUtils.isEmpty(this.label)) {
			return this.getName();
		}
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

	public boolean isSecured() {
		return this.secured != null ? this.secured : false;
	}
	
	public Boolean getSecured() {
		return this.secured;
	}

	public void setSecured(Boolean secured) {
		this.secured = secured;
	}

	public String getSecurityFilterSql() {
		return this.securityFilterSql;
	}

	public void setSecurityFilterSql(String securityFilterSql) {
		this.securityFilterSql = securityFilterSql;
	}

	public boolean isSecurityFilter() {
		return this.securityFilter != null ? this.securityFilter : false;
	}
	
	public boolean isSecurityFilterSql() {
		return isSecurityFilter() && !StringUtils.isEmpty(this.getSecurityFilterSql()); 
	}
	
	@JsonIgnore
	public boolean isSecurityFilterTable() {
		return isSecurityFilter() && StringUtils.isEmpty(this.getSecurityFilterSql()); 
	}
	
	
	
	public Boolean getSecurityFilter() {
		return this.securityFilter;
	}

	public void setSecurityFilter(Boolean securityFilter) {
		this.securityFilter = securityFilter;
	}
	
    
}
