/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.List;

import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.sql.QueryGenerationCommand;

/**
 * The QueryTable class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public class QueryTable {
	
	private String tableName;
	private String alias;
	private boolean main;
	private JoinType joinType;
	private List<QueryTable> tableList;
	
	public QueryTable() {
		
	}
	
	public QueryTable(DbTable table, QueryGenerationCommand command, boolean isMain) {
		this.tableName = table.getName();
		this.alias = command.nextAlias();
		this.main = isMain;
	}
	
	public QueryTable(DbTable table, String alias) {
		this.tableName = table.getName();
		this.alias = alias;
	}
	
	private List<QueryJoin> joinList;
	
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

}
