package com.reportolia.core.model.base;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.reportolia.core.model.table.DbTableRelationship;

/**
 * 
 * The BaseColumnPath class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@MappedSuperclass
public class BaseColumnPath extends BaseEntity {
    
	@ManyToOne
    @JoinColumn(name="table_relatioship_id", nullable=false)
    private DbTableRelationship dbTableRelationship;
	
	@Column(name = "relationship_order")
	private int order;
	
	/**
	 * True when parent table in the relationship is a first table in this Path 
	 */
	@Column(name = "is_from_parent", columnDefinition = "boolean default false")
    private Boolean fromParent;


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

	public Boolean isFromParent() {
		return this.fromParent;
	}

	public void setFromParent(Boolean fromParent) {
		this.fromParent = fromParent != null ? fromParent : false;
	}
    
	
}
