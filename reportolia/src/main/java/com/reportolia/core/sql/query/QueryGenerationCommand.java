/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.sql.query.model.Query;
import com.reportolia.core.sql.query.model.QueryOperand;
import com.reportolia.core.sql.query.model.QueryTable;

/**
 * The QueryGenerationCommand class
 *
 * @author Batir Akhmerov
 * Created on Dec 1, 2015
 */
public class QueryGenerationCommand {
	
	private Query topQuery;
	private QueryTable mainQueryTable;
	private DbTable mainTable;
	private List<QueryOperand> groupByList;
	private Map<String, QueryTable> cachedAliases;
	private boolean notCorrelated;
	private QueryGenerationCommand topQueryGenerationCommand;
	private List<QueryGenerationCommand> nestedQueryGenerationCommands;
	
	public QueryGenerationCommand() {
		
	}
	
	public QueryGenerationCommand(Query topQuery, QueryTable mainQueryTable, DbTableColumn column, QueryGenerationCommand topCommand) {
		this.topQuery = topQuery;
		this.setMainQueryTable(mainQueryTable, true);
		this.mainTable = mainQueryTable.getTable();
		this.notCorrelated = column.isNotCorrelated();
		this.topQueryGenerationCommand = topCommand;
		topCommand.addNestedQueryGenerationCommand(this);
	}
	

	public void cacheAlias(QueryTable table) {
		getCachedAliases().put(table.getAlias(), table);
	}
	public QueryTable getCachedTable(String alias) {
		return getCachedAliases().get(alias);
	}


	public Query getTopQuery() {
		return this.topQuery;
	}

	public void setTopQuery(Query topQuery) {
		this.topQuery = topQuery;
	}
	
	public boolean isTopQueryAggregated() {
		return this.topQuery != null && this.topQuery.isAggregated();
	}

	public QueryTable getMainQueryTable() {
		return this.mainQueryTable;
	}

	public void setMainQueryTable(QueryTable mainQueryTable, boolean external) {
		if (external) {
			this.mainQueryTable = new QueryTable(mainQueryTable, external);
		}
		else {
			this.mainQueryTable = mainQueryTable;
		}
	}
	
	
	public void addGroupByOperandToTopQuery(QueryOperand operand) {
		if (getTopQueryGenerationCommand() == null) {
			return;
		}
		getTopQueryGenerationCommand().getGroupByList().add(operand);
	}
	public void addGroupByOperand(QueryOperand operand) {
		getGroupByList().add(operand);
	}
	public List<QueryOperand> getGroupByList() {
		if (this.groupByList == null) {
			this.groupByList = new ArrayList<>();
		}
		return this.groupByList;
	}

	public void setGroupByList(List<QueryOperand> groupByList) {
		this.groupByList = groupByList;
	}

	public boolean isNotCorrelated() {
		return this.notCorrelated;
	}

	public void setNotCorrelated(boolean notCorrelated) {
		this.notCorrelated = notCorrelated;
	}

	public DbTable getMainTable() {
		if (this.mainTable != null) { 
			return this.mainTable;
		}
		else if (this.mainQueryTable != null) {
			return this.mainQueryTable.getTable();
		}
		return null;
	}

	public void setMainTable(DbTable mainTable) {
		this.mainTable = mainTable;
	}

	public Map<String, QueryTable> getCachedAliases() {
		if (this.cachedAliases == null) {
			this.cachedAliases = new HashMap<>();
		}
		return this.cachedAliases;
	}

	public void setCachedAliases(Map<String, QueryTable> cachedAliases) {
		this.cachedAliases = cachedAliases;
	}

	public QueryGenerationCommand getTopQueryGenerationCommand() {
		return this.topQueryGenerationCommand;
	}

	public void setTopQueryGenerationCommand(QueryGenerationCommand topQueryGenerationCommand) {
		this.topQueryGenerationCommand = topQueryGenerationCommand;
	}

	public void addNestedQueryGenerationCommand(QueryGenerationCommand command) {
		getNestedQueryGenerationCommands().add(command);
	}
	
	public List<QueryGenerationCommand> getNestedQueryGenerationCommands() {
		if (this.nestedQueryGenerationCommands == null) {
			this.nestedQueryGenerationCommands = new ArrayList<>();
		}
		return this.nestedQueryGenerationCommands;
	}

	
}
