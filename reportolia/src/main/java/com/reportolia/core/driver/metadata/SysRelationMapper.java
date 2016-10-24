/**
 * 
 */
package com.reportolia.core.driver.metadata;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * The SysRelationMapper class
 *
 * @author Batir Akhmerov
 * Created on Oct 23, 2016
 */
public class SysRelationMapper implements RowMapper<SysRelation> {
	
	public SysRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new SysRelation(
			rs.getString(SysRelation.PARENT_TABLE), 
			rs.getString(SysRelation.PARENT_COLUMN), 
			rs.getString(SysRelation.CHILD_TABLE),
			rs.getString(SysRelation.CHILD_COLUMN)
		);
	}

}

