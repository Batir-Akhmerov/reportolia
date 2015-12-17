/**
 * 
 */
package com.reportolia.core.sql.query.model;

/**
 * The QueryJoin class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public class QueryJoin {
	
	private QueryColumn pkColumn;
	private QueryColumn joinColumn;
	private String joinValue;
	
	public QueryColumn getPkColumn() {
		return this.pkColumn;
	}
	public void setPkColumn(QueryColumn pkColumn) {
		this.pkColumn = pkColumn;
	}
	public QueryColumn getJoinColumn() {
		return this.joinColumn;
	}
	public void setJoinColumn(QueryColumn joinColumn) {
		this.joinColumn = joinColumn;
	}
	public String getJoinValue() {
		return this.joinValue;
	}
	public void setJoinValue(String joinValue) {
		this.joinValue = joinValue;
	}

}
