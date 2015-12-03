package com.reportolia.core.model.sqlitem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.reportolia.core.Constants;
import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.model.datatype.DataType;

/**
 * 
 * The SqlItemParameter class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@Entity
@Table(name="r3p_sql_item_params", 
	uniqueConstraints = { @UniqueConstraint(columnNames = {"sql_item_id", "param_sql"}) })
public class SqlItemParameter extends BaseEntity {
    
	@NotEmpty
    @Column(name = "sql_item_id", nullable = false)
    private long sqlItemId;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "data_type", nullable = false, length = Constants.LENGTH_DATA_TYPE)
    private DataType dataType;
    
    @NotEmpty
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    
    @NotEmpty
    @Column(name = "param_sql", nullable = false, length = 128)
    private String sql;
    
    @Column(name = "param_order")
    private int order;

	public long getSqlItemId() {
		return this.sqlItemId;
	}

	public void setSqlItemId(long sqlItemId) {
		this.sqlItemId = sqlItemId;
	}

	public DataType getDataType() {
		return this.dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
    
	
}
