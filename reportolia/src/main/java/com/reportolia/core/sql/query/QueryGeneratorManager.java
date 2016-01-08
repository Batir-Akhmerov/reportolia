/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.reportolia.core.handler.operand.OperandHandler;
import com.reportolia.core.handler.security.ReportoliaSecurityHandler;
import com.reportolia.core.model.base.BaseColumnPath;
import com.reportolia.core.model.datatype.DataType;
import com.reportolia.core.model.operand.Operand;
import com.reportolia.core.model.operand.OperandColumnPath;
import com.reportolia.core.model.sqlitem.SqlItem;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.sql.query.model.JoinType;
import com.reportolia.core.sql.query.model.QC;
import com.reportolia.core.sql.query.model.Query;
import com.reportolia.core.sql.query.model.QueryColumn;
import com.reportolia.core.sql.query.model.QueryJoin;
import com.reportolia.core.sql.query.model.QueryOperand;
import com.reportolia.core.sql.query.model.QueryTable;

/**
 * The QueryGeneratorManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Component
public class QueryGeneratorManager implements QueryGeneratorHandler {
	 
	protected OperandHandler contentOperandHandler; // SELECT clause operand handler - single column
	protected OperandHandler filterOperandHandler;	// WHERE clause operand handler  
	protected OperandHandler sortingOperandHandler;	// ORDER BY clause operand handler
	
	protected QueryGeneratorManager nestedQueryGeneratorHandler; // used for query generation of nested calculated columns inside Operands within SELECT or WHERE clauses
	
	
	@Resource protected DbTableColumnRepository tableColumnRepository;
	@Resource protected DbTableRelationshipRepository tableRelationshipRepository;	
	@Resource protected ReportoliaSecurityHandler reportoliaSecurityHandler;
	
	public OperandHandler getContentOperandHandler() {
		return this.contentOperandHandler;
	}
	public void setContentOperandHandler(OperandHandler contentOperandHandler) {
		this.contentOperandHandler = contentOperandHandler;
	}
	public OperandHandler getFilterOperandHandler() {
		return this.filterOperandHandler;
	}
	public void setFilterOperandHandler(OperandHandler filterOperandHandler) {
		this.filterOperandHandler = filterOperandHandler;
	}
	public OperandHandler getSortingOperandHandler() {
		return this.sortingOperandHandler;
	}
	public void setSortingOperandHandler(OperandHandler sortingOperandHandler) {
		this.sortingOperandHandler = sortingOperandHandler;
	}
	public QueryGeneratorManager getNestedQueryGeneratorHandler() {
		return this.nestedQueryGeneratorHandler;
	}
	public void setNestedQueryGeneratorHandler(QueryGeneratorManager nestedQueryGeneratorHandler) {
		this.nestedQueryGeneratorHandler = nestedQueryGeneratorHandler;
	}
	
	
	
	
	 
	public Query getQuery(Long ownerId, QueryTable pkQueryTable, QueryGenerationCommand command) {
		Query query = new Query();
		if (command.getTopQuery() != null) {
			query.setTopQuery(command.getTopQuery());
		}
		query.setTop1();
		
		QueryTable qMainTable = new QueryTable(command.getMainTable(), command, true, true);
		query.addTable(qMainTable);
		if (query.isSecured()) {
			appendFilterByQueryTable(query, qMainTable, "", command);
		}
		
		// 1. Column Content
		List<Operand> contentList = this.contentOperandHandler.getOperandsByOwner(ownerId);
		if (CollectionUtils.isEmpty(contentList)) {
			throw new ValidationException("Please add Operands to the Column Content!");
		}
		 
		List<QueryOperand> qList = createQueryOperands(query, contentList, command);
		QueryColumn qColumn = new QueryColumn(qList);
		
		query.addColumn(qColumn);
		 
		 
		// 2. Static Filter
		List<Operand> filterList = this.filterOperandHandler.getOperandsByOwner(ownerId);
		qList = createFilterCorrelation(query, qMainTable, pkQueryTable, command);
		qList.addAll(createQueryOperands(query, filterList, command));
		query.setFilterList(qList);
		 
		 
		// 3. Sorting
		List<Operand> sortingList = this.sortingOperandHandler.getOperandsByOwner(ownerId);
		qList = createQueryOperands(query, sortingList, command);
		query.setSortingList(qList);
		 
		
		// 4. Group By
		if (CollectionUtils.isEmpty(command.getGroupByList())) {
			 query.setGroupList(command.getGroupByList());
		}		 
		 
		return query;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// OPERAND LIST begin ///////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected List<QueryOperand> createFilterCorrelation(Query query, QueryTable qMainTable, QueryTable pkQueryTable, QueryGenerationCommand command) {
		List<QueryOperand> qOperandList = new ArrayList<>();
		if (command.isNotCorrelated()) {
			return qOperandList;
		}
		Assert.isTrue(qMainTable != null, "qMainTable is required!");
		Assert.isTrue(pkQueryTable != null, "pkQueryTable is required!");
		
		List<DbTableColumn> pkColumns = tableColumnRepository.findByDbTableAndPk(qMainTable.getTable(), true);
		Assert.isTrue(!CollectionUtils.isEmpty(pkColumns), "Cannot find PK Column for table " + qMainTable.getTable().getName());
		boolean isFirst = true;
		for (DbTableColumn pk: pkColumns) {
			if (!isFirst) {
				qOperandList.add(new QueryOperand(QC.AND));
			}
			qOperandList.add(new QueryOperand(pk, qMainTable));
			qOperandList.add(new QueryOperand(QC.EQ));
			qOperandList.add(new QueryOperand(pk, pkQueryTable));
			isFirst = false;
		}
		
		return qOperandList;
	}
	
	public List<QueryOperand> createQueryOperands(Query query, List<Operand> operandList, QueryGenerationCommand command) {
		List<QueryOperand> qOperandList = new ArrayList<>();
		
		if (CollectionUtils.isEmpty(operandList)) {
			return qOperandList;
		}
		
		for (Operand operand: operandList) {
			QueryOperand qOperand = null;
			// 1. Operand column associated with table column directly
			if (operand.getDbColumn() != null) {
				qOperand = createQueryOperandFromColumn(query, operand, command);
			}
			else if (operand.getSqlItemType() != null) {
				qOperand = createQueryOperandFromSqlItem(query, operand, command);
			}
			else if (operand.getVariable() != null) {
				qOperand = createQueryOperandFromVariable(query, operand, command);
			}
			else if (!StringUtils.isEmpty(operand.getValue())) {
				qOperand = createQueryOperandFromValue(query, operand, command);
			}
			else {
				throw new ValidationException("Unsupported operand type!");
			}
			qOperandList.add(qOperand);
		}
		
		return qOperandList;
	}
	
	protected QueryOperand createQueryOperandFromColumn(Query query, Operand operand, QueryGenerationCommand command) {
		Assert.isTrue(operand.getDbColumn() != null, "Operand.TableColumn cannot be null!");
		DbTableColumn tbColumn = operand.getDbColumn();
		
		List<OperandColumnPath> columnPathList = this.contentOperandHandler.getOperandColumnPathsByOperand(operand);
		QueryTable qColumnTable = appendTablesToQuery(query, columnPathList, command);
		
		QueryOperand qOperand = null;
		if (tbColumn.isCalculated() == null || !tbColumn.isCalculated()) {
			qOperand = new QueryOperand(tbColumn, qColumnTable);
		}
		else {
			Assert.isTrue(this.nestedQueryGeneratorHandler != null, "NestedQueryGeneratorHandler is expected!");
			QueryGenerationCommand nestedCommand = new QueryGenerationCommand();
			nestedCommand.setTopQuery(query);
			nestedCommand.setMainTable(qColumnTable.getTable());
			Query nestedQuery = this.nestedQueryGeneratorHandler.getQuery(tbColumn.getId(), qColumnTable, nestedCommand);
			qOperand = new QueryOperand(nestedQuery);
		}
		return qOperand;
	}
	
	protected QueryOperand createQueryOperandFromSqlItem(Query query, Operand operand, QueryGenerationCommand command) {
		Assert.isTrue(operand.getSqlItem() != null, "Operand.SqlItem cannot be null!");
		QueryOperand qOperand = null;
		SqlItem item = operand.getSqlItem();
		if (CollectionUtils.isEmpty(item.getSqlItemParameters())) {
			qOperand = new QueryOperand(operand.getSqlItem());
		}
		else {
			throw new ValidationException("Implement parameterized SqlItems here!");
		}
		
		return qOperand;
	}
	protected QueryOperand createQueryOperandFromVariable(Query query, Operand operand, QueryGenerationCommand command) {
		Assert.isTrue(operand.getVariable() != null, "Operand.Variable cannot be null!");
		QueryOperand qOperand = new QueryOperand(operand.getVariable());
		return qOperand;
	}
	protected QueryOperand createQueryOperandFromValue(Query query, Operand operand, QueryGenerationCommand command) {
		Assert.isTrue(!StringUtils.isEmpty(operand.getValue()), "Operand.Value cannot be null!");
		// TODO: implement comma-delimited values parsing
		List<Object> valueList = new ArrayList<>();
		valueList.add(operand.getValue()); 
		QueryOperand qOperand = new QueryOperand(valueList, DataType.TXT);
		return qOperand;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// OPERAND LIST end   ///////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// QUERY TABLE LIST begin ///////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public <T extends BaseColumnPath> QueryTable appendTablesToQuery(Query query, List<T> columnPathList, QueryGenerationCommand command) {
		QueryTable qMainTable = query.getMainTable();
		QueryTable qTable = qMainTable;
		 
		if (CollectionUtils.isEmpty(columnPathList)) { // no stamps means main query table is a column table 
			return qTable;
		}
		 
		StringBuilder stamp = new StringBuilder(qMainTable.getAlias() + QC.UNDERSCORE);
		
		QueryTable leftJoinedQTable = null;
		for (T path: columnPathList) {
			DbTableRelationship rel = path.getDbTableRelationship();
			 
			qTable = appendTableJoin(query, qTable, path.isFromParent(), rel, stamp, leftJoinedQTable, command);
			
			if (query.isSecured()) {
				appendFilterByQueryTable(query, qTable, stamp.toString(), command);
			}
			
			if (qTable.getJoinType() == JoinType.LEFT) {
				leftJoinedQTable = qTable;
			}
		}
		return qTable;
	}
		
	public void appendFilterByQueryTable(Query query, QueryTable qTable, String stamp, QueryGenerationCommand command) {
		DbTable table = qTable.getTable();
		if (!table.isSecured() || table.isSecurityFilter()) { // || !StringUtils.isEmpty(table.getSecurityFilterSql())) {
			return;
		}
		SecurityFilterConfig conf = new SecurityFilterConfig();
		
		findLinksToSecurityFilters(table, conf);
		
		if (CollectionUtils.isEmpty(conf.getListOfLinksToFilters())) {  
			throw new ValidationException("Table [" + table.getName() + "] is secured. However there are no parent relatioships leading to parent tables with security filters!");
		}
		
		
		for (List<DbTableRelationship> linksToFilters: conf.getListOfLinksToFilters()) {
			
			StringBuilder filterStamp = new StringBuilder(stamp);
			QueryTable leftJoinedQTable = null;
			
			for (DbTableRelationship rel: linksToFilters) {
				
				qTable = appendTableJoin(query, qTable, false, rel, filterStamp, leftJoinedQTable, command);
				if (table.isSecurityFilter()) {
					break;
				}
				
				if (qTable.getJoinType() == JoinType.LEFT) {
					leftJoinedQTable = qTable;
				}
			}
		}
		
	}
	 
	protected <T extends BaseColumnPath> QueryTable appendTableJoin(Query query, QueryTable qTable, boolean isParentTableFirst, DbTableRelationship rel, StringBuilder stamp, QueryTable parentQTable, QueryGenerationCommand command) {
			 
		DbTableColumn childColumn = rel.getDbColumnChild();
		DbTable childTable = childColumn.getDbTable();
		 
		DbTableColumn parentColumn = rel.getDbColumnParent();
		DbTable parentTable = parentColumn.getDbTable();
		 
		DbTable nextTable = parentTable; // assuming most table path follow from child to parent tables in the path
		DbTableColumn nextColumn = parentColumn;
		DbTableColumn prevColumn = childColumn;
		 
		//boolean isParentTableFirst = path.isFromParent();
		 
		String fromChildMarker = "";
		String aliasPrefix = "";
		JoinType joinType = JoinType.INNER;
		if (isParentTableFirst) { // determine which table goes first in the path
			if (rel.getJoinTypeToChild() != null) {
				joinType = rel.getJoinTypeToChild();
			}
			Assert.isTrue(parentTable.getName() == qTable.getTableName());
			nextTable = childTable;
			nextColumn = childColumn;
			prevColumn = parentColumn;
		}
		else {
			if (rel.getJoinTypeToParent() != null) {
				joinType = rel.getJoinTypeToParent();
			}
			Assert.isTrue(childTable.getName() == qTable.getTableName());				 
			fromChildMarker = QC.MARKER_PATH_FROM_CHILD;
		}
		
		if (nextTable.isSecurityFilterTable()) {
			aliasPrefix = QC.SEC_FILTER_ALIAS;
		}
		 
		String alias = aliasPrefix + stamp.toString() + fromChildMarker + rel.getId();
		 
		QueryTable prevQTable = qTable;
		qTable = command.getCachedTable(alias);
		if (qTable == null) {
			qTable = new QueryTable(nextTable, alias);
			
			qTable.setJoinType(joinType);				 
			appendQueryJoin(prevQTable, prevColumn, qTable, nextColumn, rel, isParentTableFirst);
			
			addTableToOwner(qTable, query, parentQTable, command);
			
			if (nextTable.isSecurityFilterSql()) {
				addSqlFilterToOwner(nextTable, alias, prevQTable, query, parentQTable, command);
			}
			
		}
		
		// update stamp
		if (!StringUtils.isEmpty(fromChildMarker)) {
			 stamp.append(fromChildMarker);
		}
		stamp.append(rel.getId());
		stamp.append(QC.UNDERSCORE);
		 
		return qTable;
	}
	
	public void addSqlFilterToOwner(DbTable filterTable, String alias, QueryTable prevQTable, Query query, QueryTable parentQTable, QueryGenerationCommand command) {
		if (!filterTable.isSecurityFilterSql()) {
			return;
		}
		String filterAlias = QC.SEC_FILTER_ALIAS + alias;
		QueryTable qTableSecFilter = command.getCachedTable(filterAlias);
		if (qTableSecFilter == null) {
			qTableSecFilter = new QueryTable(filterTable, filterAlias);
			qTableSecFilter.setSecurityFilterSql(replaceTableAliasMarkers(filterTable.getSecurityFilterSql(), prevQTable, qTableSecFilter));
			addTableToOwner(qTableSecFilter, query, parentQTable, command);
		}
	}
	
	protected void addTableToOwner(QueryTable qTable, Query query, QueryTable parentQTable, QueryGenerationCommand command) {
		if (parentQTable != null) {
			 parentQTable.addTable(qTable);
		}
		else {
			 query.addTable(qTable);
		}
		command.cacheAlias(qTable);
	}
	 
	protected void appendQueryJoin(QueryTable prevQTable, DbTableColumn prevColumn, QueryTable nextQTable, DbTableColumn nextColumn, DbTableRelationship rel, boolean isParentTableFirst){
		QueryJoin qJoin = createQueryJoin(prevQTable, prevColumn, nextQTable, nextColumn, rel);		 
		nextQTable.addQueryJoin(qJoin);
		 
		List<DbTableRelationship> groupRelList = rel.getDbTableRelationshipGroupList();
		if (!CollectionUtils.isEmpty(groupRelList)) {
			 for (DbTableRelationship relChild: groupRelList) {
				 
				 DbTableColumn childColumn = relChild.getDbColumnChild();
				 DbTable childTable = childColumn != null ? childColumn.getDbTable() : null;
				 
				 DbTableColumn parentColumn = relChild.getDbColumnParent();
				 DbTable parentTable = parentColumn != null ? parentColumn.getDbTable() : null;
				 
				 if (isParentTableFirst) { // determine which table goes first in the path
					 Assert.isTrue(parentTable == null || parentTable.getName() == prevQTable.getTableName());
					 nextColumn = childColumn;
					 prevColumn = parentColumn;
				 }
				 else {
					 Assert.isTrue(childTable == null || childTable.getName() == nextQTable.getTableName());
					 nextColumn = parentColumn;
					 prevColumn = childColumn;
				 }
				 
				 qJoin = createQueryJoin(prevQTable, prevColumn, nextQTable, nextColumn, relChild);		 
				 nextQTable.addQueryJoin(qJoin);
			 }
		}
	}
	 
	protected QueryJoin createQueryJoin(QueryTable prevQTable, DbTableColumn prevColumn, QueryTable nextQTable, DbTableColumn nextColumn, DbTableRelationship rel){
		QueryJoin qJoin = new QueryJoin();
		 
		if (StringUtils.isEmpty(rel.getJoinValue())) {
			 qJoin.setPkColumn(new QueryColumn(prevColumn, prevQTable));
			 qJoin.setJoinColumn(new QueryColumn(nextColumn, nextQTable));
		}
		else {
			 if (prevColumn != null) {
				 qJoin.setPkColumn(new QueryColumn(prevColumn, prevQTable));
			 }
			 else if (nextColumn != null) {
				 qJoin.setJoinColumn(new QueryColumn(nextColumn, nextQTable));
			 }
			 
			 String value = rel.getJoinValue();
			 //value = replaceTableAliasMarkers(value, prevQTable, nextQTable); // value is a '?' sql value in prepared statement
			 qJoin.setJoinValue(value);
		}
		return qJoin;
	}
	 
	protected String replaceTableAliasMarkers(String sql, QueryTable prevQTable, QueryTable nextQTable){
		
		if (StringUtils.isEmpty(sql)) {
			 return sql;
		}
		sql = sql.replace(QC.TBL_ALIAS_PARENT, prevQTable.getAlias());
		sql = sql.replace(QC.TBL_ALIAS_CHILD, nextQTable.getAlias());
		sql = sql.replace(QC.TBL_ALIAS_FILTER, nextQTable.getAlias());
		if (this.reportoliaSecurityHandler != null) {
			sql = sql.replace(QC.MARKER_USER_ID, String.valueOf(reportoliaSecurityHandler.getUserId()));
		}
			
		return sql;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// QUERY TABLE LIST end /////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// SECURITY FILTER begin ////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected void findLinksToSecurityFilters(DbTable table, SecurityFilterConfig conf){
		List<DbTableRelationship> linksToFilter = new ArrayList<>();
		findLinksToSecurityFilters(table, conf, linksToFilter);
	}
	
	protected void findLinksToSecurityFilters(DbTable table, SecurityFilterConfig conf, List<DbTableRelationship> linksToFilter){
		if (table.isSecurityFilter()) { // || !StringUtils.isEmpty(table.getSecurityFilterSql())) {
			return; // security filter is found stop searching
		}
			
		List<DbTableRelationship> securedRelatioships = this.tableRelationshipRepository.findSecuredByChildTable(table.getId());
		if (CollectionUtils.isEmpty(securedRelatioships)) { // no stamps means main query table is a column table 
			throw new ValidationException("Table [" + table.getName() + "] is secured. However there are no parent relatioships leading to parent tables with security filters!");
		}
		boolean isFirst = true;
		for (DbTableRelationship rel: securedRelatioships) {
			List<DbTableRelationship> links = null;
			if (!conf.addToUniqueSet(rel.getId())) {
				throw new ValidationException("Circular relationships is detected when trying to find security filters for table [" + table.getName() + "]!"
						+ " Relationship with ID ["+rel.getId()+"] from child table ["+rel.getChildTable().getName()
						+"] to parent table ["+rel.getParentTable().getName()+"] was already processed! "
								+ "Please remove circularity in table relationships to security filters and make all links straight!");
			}
			if (isFirst) {
				linksToFilter.add(rel);
				links = linksToFilter;
				isFirst = false;
			}
			else {
				links = new ArrayList<>();
				links.addAll(linksToFilter);
				conf.addAnotherListOfLinks(links);
			}
			findLinksToSecurityFilters(rel.getParentTable(), conf, links);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// SECURITY FILTER end /////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	 
}
