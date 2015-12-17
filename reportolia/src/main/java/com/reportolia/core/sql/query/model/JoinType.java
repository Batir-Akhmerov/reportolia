/**
 * 
 */
package com.reportolia.core.sql.query.model;

/**
 * The JoinType class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public enum JoinType {
	INNER("INNER JOIN"),
	LEFT("LEFT JOIN");
	
	String sql;
	
	JoinType(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return this.sql;
	}
	

}
