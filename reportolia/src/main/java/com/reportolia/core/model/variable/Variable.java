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
 * The Variable class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_variables", uniqueConstraints = { @UniqueConstraint(columnNames = {"owner_type", "owner_id", "name"}) })
public class Variable extends BaseEntity {
    
	@Enumerated(EnumType.STRING)
	@Column(name = "owner_type", nullable = false, length = Constants.LENGTH_OWNER_TYPE)
    private VariableOwnerType ownerType;
	
    @Column(name = "owner_id")
    private Long ownerId;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "data_type", nullable = false, length = Constants.LENGTH_DATA_TYPE)
    private DataType dataType;
    
    /**
     * Not-correlated calculated column used as value source to select variable values from
     */
    @ManyToOne
    @JoinColumn(name="value_source_column_id", nullable=false)
    private DbTableColumn valueSourceDbColumn;
    
    
    @Column(name = "name", nullable = false, length = 128)
    private String name;


	/**
	 *
	 * @return the ownerType
	 */
	public VariableOwnerType getOwnerType() {
		return this.ownerType;
	}


	/**
	 *
	 * @param ownerType the ownerType to set
	 */
	public void setOwnerType(VariableOwnerType ownerType) {
		this.ownerType = ownerType;
	}


	/**
	 *
	 * @return the ownerId
	 */
	public Long getOwnerId() {
		return this.ownerId;
	}


	/**
	 *
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}


	/**
	 *
	 * @return the dataType
	 */
	public DataType getDataType() {
		return this.dataType;
	}


	/**
	 *
	 * @param dataType the dataType to set
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}


	/**
	 *
	 * @return the valueSourceDbColumn
	 */
	public DbTableColumn getValueSourceDbColumn() {
		return this.valueSourceDbColumn;
	}


	/**
	 *
	 * @param valueSourceDbColumn the valueSourceDbColumn to set
	 */
	public void setValueSourceDbColumn(DbTableColumn valueSourceDbColumn) {
		this.valueSourceDbColumn = valueSourceDbColumn;
	}


	/**
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}


	/**
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
    
    
	
}
