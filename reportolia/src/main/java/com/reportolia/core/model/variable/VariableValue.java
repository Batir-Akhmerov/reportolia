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
import com.reportolia.core.model.datatype.DataType;
import com.reportolia.core.model.table.DbTableColumn;

/**
 * 
 * The VariableValue class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_variable_values", uniqueConstraints = { @UniqueConstraint(columnNames = {"variable_id", "contact_id", "value"}) })
public class VariableValue extends BaseEntity {
    
	@Enumerated(EnumType.STRING)
	@Column(name = "owner_type", nullable = false, length = Constants.LENGTH_OWNER_TYPE)
    private VariableOwnerType ownerType;
	
    @Column(name = "variable_id")
    private Long variableId;
    
    /**
     * Used for user specific variable - values
     */
    @Column(name = "contact_id")
    private Long contactId;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "data_type", nullable = false, length = Constants.LENGTH_DATA_TYPE)
    private DataType dataType;
    
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
     * Not-correlated calculated column used as value source to select variable values from
     */
    @ManyToOne
    @JoinColumn(name="value_source_column_id", nullable=false)
    private DbTableColumn valueSourceDbColumn;
    
    
    @Column(name = "value", nullable = false, length = 128)
    private String value;


	public VariableOwnerType getOwnerType() {
		return this.ownerType;
	}


	public void setOwnerType(VariableOwnerType ownerType) {
		this.ownerType = ownerType;
	}


	public Long getVariableId() {
		return this.variableId;
	}


	public void setVariableId(Long variableId) {
		this.variableId = variableId;
	}


	public Long getContactId() {
		return this.contactId;
	}


	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}


	public DataType getDataType() {
		return this.dataType;
	}


	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}


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


	public DbTableColumn getValueSourceDbColumn() {
		return this.valueSourceDbColumn;
	}


	public void setValueSourceDbColumn(DbTableColumn valueSourceDbColumn) {
		this.valueSourceDbColumn = valueSourceDbColumn;
	}


	public String getValue() {
		return this.value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	
}
