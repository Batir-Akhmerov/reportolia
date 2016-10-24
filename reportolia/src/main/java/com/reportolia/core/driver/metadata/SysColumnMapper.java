/**
 * 
 */
package com.reportolia.core.driver.metadata;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * The SysColumnMapper class
 *
 * @author Batir Akhmerov
 * Created on Oct 13, 2016
 */
public class SysColumnMapper implements RowMapper<SysColumn> {
	
	public SysColumn mapRow(ResultSet rs, int rowNum) throws SQLException {
		String type = null;
		String size = null;
		try {
			type = rs.getString(SysColumn.COL_TYPE_NAME);
		}
		catch (Exception e) {
			// do nothing, type may not be in this result
		}
		try {
			size = rs.getString(SysColumn.COL_SIZE);
		}
		catch (Exception e) {
			// do nothing, size may not be in this result
		}
		
		return new SysColumn(
			"",
			rs.getString(SysColumn.COL_NAME), 
			type, 
			size
		);
	}

}

