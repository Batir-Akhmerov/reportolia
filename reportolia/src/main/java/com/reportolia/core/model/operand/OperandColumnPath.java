package com.reportolia.core.model.operand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.model.table.DbTableRelationship;

/**
 * 
 * The OperandColumnPath class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@Entity
@Table(name="r3p_operand_column_path")
public class OperandColumnPath extends BaseEntity {
    
	
    @NotEmpty
	@ManyToOne
    @JoinColumn(name="operand_id", nullable=false)
    private Operand operand;
	
	
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

	public Operand getOperand() {
		return this.operand;
	}

	public void setOperand(Operand operand) {
		this.operand = operand;
	}

	public Boolean isFromParent() {
		return this.fromParent;
	}

	public void setFromParent(Boolean fromParent) {
		this.fromParent = fromParent != null ? fromParent : false;
	}
    
	
}
