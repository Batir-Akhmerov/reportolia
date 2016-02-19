package com.reportolia.core.model.datatype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.reportolia.core.Constants;
import com.reportolia.core.model.base.BaseEntity;

/**
 * 
 * The DataTypeFormat class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_data_type_formats", uniqueConstraints = { @UniqueConstraint(columnNames = {"data_type", "format_mask"}) })
public class DataTypeFormat extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "data_type", nullable = false, length = Constants.LENGTH_DATA_TYPE)
    private DataType dataType;
    
    @Column(name = "format_mask", nullable = false, length = 128)
    private String mask;


	public DataType getDataType() {
		return this.dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
    
	public String getMask() {
		return this.mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}


    
}
