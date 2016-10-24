/**
 * 
 */
package com.reportolia.core.handler;

import java.util.List;

import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.web.controllers.JTableJsonSearchForm;

/**
 * The DbHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface DbHandler {
	
	public List<DbTable> getTableList(JTableJsonSearchForm form);
	
	public List<DbTable> getTableList(String name);
	
	public List<DbTableRelationship> getTableChildRelationshipList(long  tableId);
	
	public List<DbTableColumn> getColumnList(DbTable table);
	
	public DbTableColumn getColumn(DbTable table, String columnName);

}
