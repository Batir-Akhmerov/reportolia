/**
 * 
 */
package com.reportolia.core.driver;

/**
 * The BaseDatabaseDriver class
 *
 * @author Batir Akhmerov
 * Created on May 8, 2016
 */
public class BaseDatabaseDriver implements DatabaseDriver {
	
	public static final String TO_VARCHAR_START = "CAST( ( ";
	public static final String TO_VARCHAR_END = " ) AS VARCHAR(100))";

	@Override
	public String toVarcharStart() {
		return TO_VARCHAR_START;
	}

	@Override
	public String toVarcharEnd() {
		return TO_VARCHAR_END;
	}

}
