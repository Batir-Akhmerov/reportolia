/**
 * 
 */
package com.reportolia.core.sql.query;

import java.util.List;

/**
 * The SqlGeneratorHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public interface SqlGeneratorHandler {
	
	public String toSql(Query query, List<Object> valueList);
	
	//public String toSql(QueryTable table);
	
	//public String toSql(QueryColumn column);
}
