/**
 * 
 */
package com.reportolia.core.handler;

import java.util.List;

import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableRelationship;

/**
 * The DbHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface DbHandler {
	
	public List<DbTable> getTableList(String name);
	
	public List<DbTableRelationship> getTableChildRelationshipList(long  tableId);

}
