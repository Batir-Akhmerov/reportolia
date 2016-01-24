package com.reportolia.core;

import org.junit.Test;

/**
 * 
 * The Report4_StaticFilterOperands_Test class
 *
 * @author Batir Akhmerov
 * Created on Dec 11, 2015
 */
public class Report40_StaticFilterOperands_Test extends BaseReportTest{

	@Test
    public void reportTest() {
			
		testReportSql("Simple Table Calculated Column Test",
			40L, 
			" SELECT  "
					+ "tbl50.name "
					+ ",tbl50.price "
					+ ", ( SELECT TOP 1 "
							+ "_tbl50_2.quantity "
						+ "FROM orders _tbl50_2 "
						+ "WHERE _tbl50_2.product_id = tbl50.id "
					+ ") "
					+ ", ( SELECT TOP 1 _tbl50_2.quantity FROM orders _tbl50_2 ) "
			+ "FROM products tbl50 "
			+ "WHERE tbl50.name = ? "
			+ "ORDER BY  tbl50.name DESC "
		, "Test Product");
		
    }
	
	
	
	
}
