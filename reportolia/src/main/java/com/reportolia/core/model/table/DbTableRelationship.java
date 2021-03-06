package com.reportolia.core.model.table;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reportolia.core.Constants;
import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.sql.query.model.JoinType;

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
     * This Join Type is only used when Parent joins its Child.
     */
    @JsonIgnore
    @Enumerated(EnumType.STRING)
	@Column(name = "join_type_to_child", nullable = true, length = Constants.LENGTH_JOIN_TYPE)
    private JoinType joinTypeToChild = JoinType.INNER;
    
    /**
     * This Join Type is only used when Child joins its Parent.
     */
    @JsonIgnore
    @Enumerated(EnumType.STRING)
	@Column(name = "join_type_to_parent", nullable = true, length = Constants.LENGTH_JOIN_TYPE)
    private JoinType joinTypeToParent = JoinType.INNER;
    
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
    @JsonIgnore
    @Column(name = "join_value", length = 128)
    private String joinValue;
    
    /**
     * List of other joined columns in case if this relationship is composite 
     */
    @JsonIgnore
    @OneToMany(targetEntity=DbTableRelationship.class, mappedBy="dbTableRelationshipGroup", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("order")
    private List<DbTableRelationship> dbTableRelationshipGroupList;
    
    /**
     * Reference to the primary Table Relationship bean in a Group of composite column joins 
     */
    @ManyToOne
    @JoinColumn(name="group_relationship_id", nullable=true)
    private DbTableRelationship dbTableRelationshipGroup;
    //@Column(name = "group_relationship_id")
    //private Long groupRelationshipId;
    
    @JsonIgnore
    @Column(name = "group_relationship_order")
    private Integer order;
    
    @Column(name = "is_link_to_security_filter", columnDefinition = "boolean default false")
    private Boolean linkToSecurityFilter;
    
	
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
	
	public DbTable getParentTable() {
		return this.dbColumnParent.getDbTable();
	}
	public Long getParentTableId() {
		return this.dbColumnParent.getDbTable().getId();
	}
	
	public DbTable getChildTable() {
		return this.dbColumnChild.getDbTable();
	}
	public Long getChildTableId() {
		return this.dbColumnChild.getDbTable().getId();
	}


	public DbTableColumn getDbColumnParent() {
		return this.dbColumnParent;
	}
	public void setDbColumnParent(DbTableColumn dbColumnParent) {
		this.dbColumnParent = dbColumnParent;
	}
	public Long getDbColumnParentId() {
		return this.dbColumnParent.getId();
	}


	public DbTableColumn getDbColumnChild() {
		return this.dbColumnChild;
	}
	public void setDbColumnChild(DbTableColumn dbColumnChild) {
		this.dbColumnChild = dbColumnChild;
	}
	public Long getDbColumnChildId() {
		return this.dbColumnChild.getId();
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


	public JoinType getJoinTypeToChild() {
		return this.joinTypeToChild;
	}


	public void setJoinTypeToChild(JoinType joinTypeToChild) {
		this.joinTypeToChild = joinTypeToChild;
	}


	public List<DbTableRelationship> getDbTableRelationshipGroupList() {
		return this.dbTableRelationshipGroupList;
	}


	public void setDbTableRelationshipGroupList(List<DbTableRelationship> dbTableRelationshipGroupList) {
		this.dbTableRelationshipGroupList = dbTableRelationshipGroupList;
	}


	public Integer getOrder() {
		return this.order;
	}


	public void setOrder(Integer order) {
		this.order = order;
	}


	public JoinType getJoinTypeToParent() {
		return this.joinTypeToParent;
	}


	public void setJoinTypeToParent(JoinType joinTypeToParent) {
		this.joinTypeToParent = joinTypeToParent;
	}


	public Boolean getLinkToSecurityFilter() {
		return this.linkToSecurityFilter;
	}


	public void setLinkToSecurityFilter(Boolean linkToSecurityFilter) {
		this.linkToSecurityFilter = linkToSecurityFilter;
	}


	@Override
	public String toString() {		
		return getLabelFull();
	}

}
