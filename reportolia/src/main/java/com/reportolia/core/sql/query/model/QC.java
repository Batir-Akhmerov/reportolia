/**
 * 
 */
package com.reportolia.core.sql.query.model;

/**
 * The Query Constants class
 *
 * @author Batir Akhmerov
 * Created on Nov 25, 2015
 */
public class QC {

	public static final String TBL_ALIAS = "tbl";
	public static final String SEC_FILTER_ALIAS = "sf_";
	public static final String TBL_ALIAS_NESTED = "_";
	public static final String MARKER_PATH_FROM_CHILD = "x";
	public static final String TBL_ALIAS_PARENT = "#[{ALIAS_PARENT}]#";
	public static final String TBL_ALIAS_CHILD =  "#[{ALIAS_CHILD}]#";
	public static final String TBL_ALIAS_FILTER = "#[{ALIAS_FILTER}]#";
	public static final String MARKER_USER_ID = "#[{USER_ID}]#";
	
	
	public static final String SPACE = " ";
	public static final String UNDERSCORE = "_";
	public static final String DOT = ".";
	public static final String COMMA = ",";
	public static final String Q = "?";
	public static final String NL = "\n";
	public static final String TAB = "\t";
	
	public static final String AND = " AND ";
	public static final String ON = " ON ";
	public static final String EQ = " = ";
	public static final String PL = " ( ";
	public static final String PR = " ) ";
	
	
	public static final String TOP1 = " TOP 1 ";
	public static final String TOP = " TOP ";
	public static final String SELECT = " SELECT ";
	public static final String FROM = " FROM ";
	public static final String WHERE = " WHERE ";
	public static final String ORDER_BY = " ORDER BY ";
	public static final String GROUP_BY = " GROUP BY ";
}
