package com.reportolia.core.model.operand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.reportolia.core.Constants;
import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.model.sqlitem.SqlItem;
import com.reportolia.core.model.sqlitem.SqlItemType;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.variable.Variable;

/**
 * 
 * The Operand class
 *
 * @author Batir Akhmerov
 * Created on Nov 20, 2015
 */
@Entity
@Table(name="r3p_operands")
public class Operand extends BaseEntity {
	

	@Enumerated(EnumType.STRING)
	@Column(name = "owner_type", nullable = false, length = Constants.LENGTH_OWNER_TYPE)
	private OperandOwnerType ownerType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "content_type", nullable = false, length = Constants.LENGTH_OWNER_TYPE)
	private ContentType contentType;
   
    @Column(name = "owner_id", nullable = false)
	private Long ownerId;
	
	// SQL ITEM
	@Enumerated(EnumType.STRING)
	@Column(name = "sql_item_type", length = Constants.LENGTH_SQL_ITEM_TYPE)
    private SqlItemType sqlItemType;
	
	/*
    @Column(name = "sql_item_id")
    private Long sqlItemId;
    */
	
    @ManyToOne
    @JoinColumn(name="sql_item_id")
    private SqlItem sqlItem;
    
    
    // COLUMN
    @ManyToOne
    @JoinColumn(name="column_id")
    private DbTableColumn dbColumn;
    /* 
     * TODO: Implement order sorting through annotations
    @OneToMany
    @JoinColumn(name="operand_id")
    private List<OperandColumnPath> columnPaths;
    */
    
    // VARIABLE
    @ManyToOne
    @JoinColumn(name="variable_id")
    private Variable variable;
    
    @Column(name = "value", length = Constants.LENGTH_VARIABLE_VALUE)
    private String value;
    
    @Column(name = "is_desc_sort", columnDefinition = "boolean default false")
    private Boolean descSort;
    
    @Column(name = "operand_order")
    private Integer order;

	public OperandOwnerType getOwnerType() {
		return this.ownerType;
	}

	public void setOwnerType(OperandOwnerType ownerType) {
		this.ownerType = ownerType;
	}

	public ContentType getContentType() {
		return this.contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public SqlItemType getSqlItemType() {
		return this.sqlItemType;
	}

	public void setSqlItemType(SqlItemType sqlItemType) {
		this.sqlItemType = sqlItemType;
	}

	public DbTableColumn getDbColumn() {
		return this.dbColumn;
	}

	public void setDbColumn(DbTableColumn dbColumn) {
		this.dbColumn = dbColumn;
	}

	public Variable getVariable() {
		return this.variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean isDescSort() {
		return this.descSort;
	}

	public void setDescSort(Boolean descSort) {
		this.descSort = descSort != null ? descSort : false;
	}


	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getSqlItemId() {
		if (this.sqlItem == null) {
			return null;
		}
		return this.sqlItem.getId();
	}
	
	public SqlItem getSqlItem() {
		return this.sqlItem;
	}

	public void setSqlItem(SqlItem sqlItem) {
		this.sqlItem = sqlItem;
	}

	

}
