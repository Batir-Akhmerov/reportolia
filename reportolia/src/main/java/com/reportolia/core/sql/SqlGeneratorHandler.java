/**
 * 
 */
package com.reportolia.core.sql;

import java.util.List;

import com.reportolia.core.sql.query.model.Query;

/**
 * The SqlGeneratorHandler class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public interface SqlGeneratorHandler {
	
	public String toSql(Query query, List<Object> valueList);
	
	public String toSql(Query query, StringBuilder builder, List<Object> valueList);
	
	//public String toSql(QueryTable table);
	
	//public String toSql(QueryColumn column);
}
