package com.reportolia.core.model.operand;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.reportolia.core.model.base.BaseColumnPath;

/**
 * 
 * The OperandColumnPath class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@Entity
@Table(name="r3p_operand_column_path")
public class OperandColumnPath extends BaseColumnPath {
    
	
	@ManyToOne
    @JoinColumn(name="operand_id", nullable=false)
    private Operand operand;

	public Operand getOperand() {
		return this.operand;
	}

	public void setOperand(Operand operand) {
		this.operand = operand;
	}
	
	
		
}
