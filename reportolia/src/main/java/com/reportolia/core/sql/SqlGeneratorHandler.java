/**
 * 
 */
package com.reportolia.core.sql;

import java.util.List;

import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableRelationship;

/**
 * The DbHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface SqlGeneratorHandler {
	
	public List<DbTable> getDbTableList(String name);
	
	public List<DbTableRelationship> getDbTableChildRelationshipList(long tableId);

}
