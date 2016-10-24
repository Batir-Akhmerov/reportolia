/**
 * 
 */
package com.reportolia.core.driver.metadata;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * The SysTableMapper class
 *
 * @author Batir Akhmerov
 * Created on Oct 13, 2016
 */
public class SysTableMapper implements RowMapper<SysTable> {
	
	public SysTable mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new SysTable(
			 rs.getString(SysTable.COL_TABLE_NAME), 
			 rs.getString(SysTable.COL_TYPE_NAME), 
			 rs.getString(SysTable.COL_SCHEMA_NAME)
		);
	}

}
