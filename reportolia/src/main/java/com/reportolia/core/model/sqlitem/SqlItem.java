package com.reportolia.core.model.sqlitem;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	@Column(name = "datatabse_type", nullable = false, length = Constants.LENGTH_DATABASE_TYPE)
    private DatabaseType databaseType;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "data_type", nullable = false, length = Constants.LENGTH_DATA_TYPE)
    private DataType dataType;
        
    @Enumerated(EnumType.STRING)
	@Column(name = "sql_item_type", nullable = false, length = Constants.LENGTH_SQL_ITEM_TYPE)
    private SqlItemType sqlItemType;
    
    @OneToOne
    @JoinColumn(name="end_block_sql_item_id")
    private SqlItem endBlockSqlItem;
    
    
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
    @Column(name = "is_block")
    private boolean block;
    
    /**
     * True for an end element of a function or a block
     */
    @Column(name = "is_block_end")
    private boolean blockEnd;
    
    /**
     * True if the element is an aggregated function
     */
    @Column(name = "is_aggregate_function")
    private boolean aggregateFunction;
    
    /**
     * True if the element is a block/function that hides data types of nested content
     */
    @Column(name = "is_isolated")
    private boolean isolated;
    
    /**
     * True if the element is a nested sub function of a top level composite function
     */
    @Column(name = "is_nested")
    private boolean nestedElement;

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

	public SqlItem getEndBlockSqlItem() {
		return this.endBlockSqlItem;
	}

	public void setEndBlockSqlItem(SqlItem endBlockSqlItem) {
		this.endBlockSqlItem = endBlockSqlItem;
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

	public boolean isBlock() {
		return this.block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public boolean isBlockEnd() {
		return this.blockEnd;
	}

	public void setBlockEnd(boolean blockEnd) {
		this.blockEnd = blockEnd;
	}

	public boolean isAggregateFunction() {
		return this.aggregateFunction;
	}

	public void setAggregateFunction(boolean aggregateFunction) {
		this.aggregateFunction = aggregateFunction;
	}

	public boolean isIsolated() {
		return this.isolated;
	}

	public void setIsolated(boolean isolated) {
		this.isolated = isolated;
	}

	public boolean isNestedElement() {
		return this.nestedElement;
	}

	public void setNestedElement(boolean nestedElement) {
		this.nestedElement = nestedElement;
	}

	public List<SqlItemParameter> getSqlItemParameters() {
		return this.sqlItemParameters;
	}

	public void setSqlItemParameters(List<SqlItemParameter> sqlItemParameters) {
		this.sqlItemParameters = sqlItemParameters;
	}
  


	
}
