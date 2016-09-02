package com.reportolia.core;

import org.junit.Test;

/**
 * 
 * The Report4_StaticFilterOperands_Test class
 *
 * @author Batir Akhmerov
 * Created on Dec 11, 2015
 */
public class Report50_StaticFilterInNestedSelect_Test extends BaseReportTest{

	@Test
    public void reportTest() {
			
		testReportSql("Static Filter In Nested Select",
			50L,
			" SELECT  ( "
				+ "SELECT TOP 1 "
					+ "_tbl1_1_x2.price "
				+ "FROM orders _tbl1_1 "
					+ "INNER JOIN products _tbl1_1_x2 ON _tbl1_1.product_id = _tbl1_1_x2.id "
					+ "INNER JOIN order_status _tbl1_1_x3 ON _tbl1_1.status_id = _tbl1_1_x3.id  "
				+ "WHERE _tbl1_1_x3.name = ? AND "
					+ "_tbl1_1.customer_id = tbl1.id "
			+ ") FROM customers tbl1 ", "Active");
		
    }
	
	
	
	
}
