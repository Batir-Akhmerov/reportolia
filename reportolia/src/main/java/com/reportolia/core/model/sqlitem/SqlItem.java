package com.reportolia.core.model.sqlitem;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.reportolia.core.Constants;
import com.reportolia.core.model.DatabaseType;
import com.reportolia.core.model.base.BaseEntity;
import com.reportolia.core.model.datatype.DataType;

/**
 * 
 * The SqlItem class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Entity
@Table(name="r3p_sql_items", 
	uniqueConstraints = { @UniqueConstraint(columnNames = {"datatabse_type", "sql_item_type", "label"}) })
public class SqlItem extends BaseEntity {

    
    @Enumerated(EnumType.STRING)
	@Column(name = "datatabse_type", length = Constants.LENGTH_DATABASE_TYPE)
    private DatabaseType databaseType;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "data_type", length = Constants.LENGTH_DATA_TYPE)
    private DataType dataType;
        
    @Enumerated(EnumType.STRING)
	@Column(name = "sql_item_type", nullable = false, length = Constants.LENGTH_SQL_ITEM_TYPE)
    private SqlItemType sqlItemType;
    
    @OneToMany(targetEntity=SqlItem.class, mappedBy="parentSqlItem", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SqlItem> nestedSqlItems;
    
    @ManyToOne
    @JoinColumn(name="parent_sql_item_id")
    private SqlItem parentSqlItem;
    
    
    @OneToMany
    @JoinColumn(name="sql_item_id")
    private List<SqlItemParameter> sqlItemParameters;
    
    
    @NotEmpty
    @Column(name = "label", nullable = false, length = 128)
    private String label;
    
    @NotEmpty
    @Column(name = "sql", nullable = false, length = 255)
    private String sql;
    
    /**
     * True for a beginning element of a function or a block
     */
    @Column(name = "is_block", columnDefinition = "Boolean default false")
    private Boolean block;
    
    /**
     * True for an end element of a function or a block
     */
    @Column(name = "is_block_end", columnDefinition = "Boolean default false")
    private Boolean blockEnd;
    
    /**
     * True if the element is an aggregated function
     */
    @Column(name = "is_aggregate_function", columnDefinition = "Boolean default false")
    private Boolean aggregateFunction;
    
    /**
     * True if the element is a block/function that hides data types of nested content
     */
    @Column(name = "is_isolated", columnDefinition = "Boolean default false")
    private Boolean isolated;
    
    /**
     * True if the element is a nested sub function of a top level composite function
     */
    @Column(name = "is_nested", columnDefinition = "Boolean default false")
    private Boolean nestedElement;
    
    

	public DatabaseType getDatabaseType() {
		return this.databaseType;
	}

	public void setDatabaseType(DatabaseType databaseType) {
		this.databaseType = databaseType;
	}

	public DataType getDataType() {
		return this.dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public SqlItemType getSqlItemType() {
		return this.sqlItemType;
	}

	public void setSqlItemType(SqlItemType sqlItemType) {
		this.sqlItemType = sqlItemType;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Boolean isBlock() {
		return this.block;
	}

	public void setBlock(Boolean block) {
		this.block = block != null ? block : false;
	}

	public Boolean isBlockEnd() {
		return this.blockEnd;
	}

	public void setBlockEnd(Boolean blockEnd) {
		this.blockEnd = blockEnd != null ? blockEnd : false;
	}

	public Boolean isAggregateFunction() {
		return this.aggregateFunction;
	}

	public void setAggregateFunction(Boolean aggregateFunction) {
		this.aggregateFunction = aggregateFunction != null ? aggregateFunction : false;
	}

	public Boolean isIsolated() {
		return this.isolated;
	}

	public void setIsolated(Boolean isolated) {
		this.isolated = isolated != null ? isolated : false;
	}

	public Boolean isNestedElement() {
		return this.nestedElement;
	}

	public void setNestedElement(Boolean nestedElement) {
		this.nestedElement = nestedElement != null ? nestedElement : false;
	}

	public List<SqlItemParameter> getSqlItemParameters() {
		return this.sqlItemParameters;
	}

	public void setSqlItemParameters(List<SqlItemParameter> sqlItemParameters) {
		this.sqlItemParameters = sqlItemParameters;
	}

	public List<SqlItem> getNestedSqlItems() {
		return this.nestedSqlItems;
	}

	public void setNestedSqlItems(List<SqlItem> nestedSqlItems) {
		this.nestedSqlItems = nestedSqlItems;
	}

	public SqlItem getParentSqlItem() {
		return this.parentSqlItem;
	}

	public void setParentSqlItem(SqlItem parentSqlItem) {
		this.parentSqlItem = parentSqlItem;
	}
  


	
}
