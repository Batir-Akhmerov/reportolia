package com.reportolia.core.model.table;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.StringUtils;

import com.reportolia.core.Constants;
import com.reportolia.core.model.base.BaseEntity;

/**
 * 
 * The DbTableColumn class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_table_relationships", 
	uniqueConstraints = { @UniqueConstraint(columnNames = {"group_relationship_id", "parent_column_id", "child_column_id", "label"}) })
public class DbTableRelationship extends BaseEntity {
    
    
    @Column(name = "label", length = 100)
    private String label;
    
    /**
     * Reference to the PK Column in a Parent Table 
     */
    @ManyToOne
    @JoinColumn(name="parent_column_id", nullable=true)
    private DbTableColumn dbColumnParent;
    
    /**
     * Reference to the FK Column in a Child Table 
     */
    @ManyToOne
    @JoinColumn(name="child_column_id", nullable=true)
    private DbTableColumn dbColumnChild;
    
    /**
     * Can be used in composite relatioship to filter one of columns by a hard-coded value
     * Only one column can be filtered using the following rule COALESCE(dbColumnParent, dbColumnChild) = joinValue
     */
    @Column(name = "joinValue", length = 128)
    private String joinValue;
    
    /**
     * List of other joined columns in case if this relationship is composite 
     */
    @OneToMany(targetEntity=DbTableRelationship.class, mappedBy="dbTableRelationshipGroup", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DbTableRelationship> dbTableRelationshipGroupList;
    
    /**
     * Reference to the primary Table Relationship bean in a Group of composite column joins 
     */
    @ManyToOne
    @JoinColumn(name="group_relationship_id", nullable=true)
    private DbTableRelationship dbTableRelationshipGroup;
   
	
	/**
	 * Optional Relationship Label, helps to differentiate relationships when there are more than one relationships between 2 tables
	 * @return
	 */
	public String getLabel() {
		return this.label;
	}

	
	public String getLabelFull() {
		return StringUtils.isEmpty(getLabel()) ? this.dbColumnParent.getDbTable().getName() +  Constants.ARROW_RIGHT + this.dbColumnChild.getDbTable().getName() : getLabel();
	}


	public DbTableColumn getDbColumnParent() {
		return this.dbColumnParent;
	}


	public void setDbColumnParent(DbTableColumn dbColumnParent) {
		this.dbColumnParent = dbColumnParent;
	}


	public DbTableColumn getDbColumnChild() {
		return this.dbColumnChild;
	}


	public void setDbColumnChild(DbTableColumn dbColumnChild) {
		this.dbColumnChild = dbColumnChild;
	}


	public Set<DbTableRelationship> getDbTableRelationshipGroupList() {
		return this.dbTableRelationshipGroupList;
	}


	public void setDbTableRelationshipGroupList(Set<DbTableRelationship> dbTableRelationshipGroupList) {
		this.dbTableRelationshipGroupList = dbTableRelationshipGroupList;
	}


	public DbTableRelationship getDbTableRelationshipGroup() {
		return this.dbTableRelationshipGroup;
	}


	public void setDbTableRelationshipGroup(DbTableRelationship dbTableRelationshipGroup) {
		this.dbTableRelationshipGroup = dbTableRelationshipGroup;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getJoinValue() {
		return this.joinValue;
	}


	public void setJoinValue(String joinValue) {
		this.joinValue = joinValue;
	}

}
