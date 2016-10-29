/**
 * 
 */
package com.reportolia.core.handler.db;

import java.util.List;

import com.reportolia.core.handler.db.relationship.RelationshipInfo;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.web.controllers.jtable.JsonSearchForm;

/**
 * The DbHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface DbHandler {
	
	/*************************************************/
	/** TABLES ***************************************/
	/*************************************************/
	public List<DbTable> getTableList(JsonSearchForm form);
	
	public List<DbTable> getTableList(String name);
	
	public DbTable saveTable(DbTable bean);
	
	public void deleteTable(DbTable  bean);
		
	
	

	/*************************************************/
	/** COLUMNS **************************************/
	/*************************************************/	
	public List<DbTableColumn> getColumnList(DbTable table);
	
	public DbTableColumn getColumn(DbTable table, String columnName);
	
	

	/*************************************************/
	/** RELATIONSHIPS ********************************/
	/*************************************************/	
	public List<RelationshipInfo> getTableRelationshipInfoList(long  tableId);
	
	public RelationshipInfo saveTableRelationship(RelationshipInfo info);
	
	public DbTableRelationship saveTableRelationship(DbTableRelationship  bean);
	
	public void deleteTableRelationship(RelationshipInfo  info);
	
	public void deleteTableRelationship(DbTableRelationship  bean);
	
	public SpringyDataBean getDataModelJson();

}
