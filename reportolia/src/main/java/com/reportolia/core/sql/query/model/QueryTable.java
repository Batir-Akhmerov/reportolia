/**
 * 
 */
package com.reportolia.core.sql.query.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.sql.query.QueryGenerationCommand;

/**
 * The QueryTable class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public class QueryTable {
	
	private DbTable table;
	private String tableName;
	private String alias;
	private boolean main;
	private boolean external; // indicates that this table is a table from the top level query object
	private JoinType joinType;
	private String securityFilterSql;
	private List<QueryTable> tableList;
	private List<QueryJoin> joinList;
	
	public QueryTable() {
		
	}
	public QueryTable(DbTable table, QueryGenerationCommand command, boolean isMain) {
		this(table, command, isMain, false);
	}
	public QueryTable(DbTable table, QueryGenerationCommand command, boolean isMain, boolean isNested) {
		this.table = table;
		this.tableName = table.getName();
		if (isMain) {
			this.alias = QC.TBL_ALIAS + table.getId();
		}
		if (isNested) {
			this.alias = QC.TBL_ALIAS_NESTED + this.alias;
		}
		this.main = isMain;
	}
	
	public QueryTable(DbTable table, String alias) {
		this.table = table;
		this.tableName = table.getName();
		this.alias = alias;
	}
	
	public QueryTable(QueryTable tb, boolean external) {
		this.table = tb.getTable();
		this.tableName = tb.getTableName();
		this.alias = tb.getAlias();
		this.main = tb.isMain();
		this.joinType = tb.getJoinType();
		this.joinList = tb.getJoinList();
		this.securityFilterSql = tb.getSecurityFilterSql();
		this.tableList = tb.getTableList();
		this.external = external;
		
	}
	
	@Override
	public String toString() {
		return this.tableName + " " + this.alias;
	}
	
	public void addQueryJoin(QueryJoin join) {
		if (CollectionUtils.isEmpty(this.joinList)) {
			this.joinList = new ArrayList<>();
		}
		this.joinList.add(join);
	}
	
	public void addTable(QueryTable table) {
		if (CollectionUtils.isEmpty(this.tableList)) {
			this.tableList = new ArrayList<>();
		}
		this.tableList.add(table);
	}
	
	public String getTableName() {
		return this.tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getAlias() {
		return this.alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public boolean isMain() {
		return this.main;
	}
	public void setMain(boolean main) {
		this.main = main;
	}
	public JoinType getJoinType() {
		return this.joinType;
	}
	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}
	public List<QueryTable> getTableList() {
		return this.tableList;
	}
	public void setTableList(List<QueryTable> tableList) {
		this.tableList = tableList;
	}
	public List<QueryJoin> getJoinList() {
		return this.joinList;
	}
	public void setJoinList(List<QueryJoin> joinList) {
		this.joinList = joinList;
	}
	public DbTable getTable() {
		return this.table;
	}
	public void setTable(DbTable table) {
		this.table = table;
	}
	public String getSecurityFilterSql() {
		return this.securityFilterSql;
	}
	public void setSecurityFilterSql(String securityFilterSql) {
		this.securityFilterSql = QC.SPACE + securityFilterSql + QC.SPACE;
	}
	public boolean isSecurityFilterSql() {
		return !StringUtils.isEmpty(this.getSecurityFilterSql()); 
	}
	public boolean isExternal() {
		return this.external;
	}
	public void setExternal(boolean external) {
		this.external = external;
	}

}
