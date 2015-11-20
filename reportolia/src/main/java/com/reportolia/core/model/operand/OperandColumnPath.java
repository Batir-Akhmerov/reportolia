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
    @Column(name = "operand_id", nullable = false)
    private long operandId;
	
	@ManyToOne
    @JoinColumn(name="table_relatioship_id", nullable=false)
    private DbTableRelationship dbTableRelationship;
	
	@Column(name = "order")
	private int order;

	public long getOperandId() {
		return this.operandId;
	}

	public void setOperandId(long operandId) {
		this.operandId = operandId;
	}

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
    
	
}
