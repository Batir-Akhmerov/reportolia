package com.reportolia.core.model.variable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.reportolia.core.Constants;
import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.model.report.ReportColumn;

/**
 * 
 * The VariableValue class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_variable_values", uniqueConstraints = { @UniqueConstraint(columnNames = {"variable_id", "consumer_id", "consumer_type", "user_id", "value"}) })
public class VariableValue extends BaseEntity {
    
    
    @ManyToOne
    @JoinColumn(name="variable_id", nullable=false)
    private Variable variable;
    
    /**
     * Variable Values can be set specifically for Operands, Report Columns, etc. 
     * For example: ReportColumn setting parameter values for table calculated column it represents.  
     */
    @Enumerated(EnumType.STRING)
	@Column(name = "consumer_type", length = Constants.LENGTH_OWNER_TYPE)
    private VariableValueConsumerType consumerType;
	
    @Column(name = "consumer_id")
    private Long consumerId;
    
    /**
     * Used for user specific variable - values
     */
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * VALUE: Hard-coded variable value to be injected into a report sql as '?'
     */
    @Column(name = "value", nullable = false, length = 128)
    private String value;
    
    /**
     * VALUE: When used within a report - existing ReportColumn whose QueryColumn Sql is injected into a report sql   
     */
    @ManyToOne
    @JoinColumn(name="report_column_id", nullable=true)
    private ReportColumn reportColumn;
    


	public VariableValueConsumerType getConsumerType() {
		return this.consumerType;
	}


	public void setConsumerType(VariableValueConsumerType consumerType) {
		this.consumerType = consumerType;
	}


	public Long getConsumerId() {
		return this.consumerId;
	}


	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}


	public String getValue() {
		return this.value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public Long getUserId() {
		return this.userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public ReportColumn getReportColumn() {
		return this.reportColumn;
	}


	public void setReportColumn(ReportColumn reportColumn) {
		this.reportColumn = reportColumn;
	}


	public Variable getVariable() {
		return this.variable;
	}


	public void setVariable(Variable variable) {
		this.variable = variable;
	}


	
}
