package com.reportolia.core.test;

import org.junit.Test;

/**
 * 
 * The Report50_StaticFilterInNestedSelect_Test class
 *
 * @author Batir Akhmerov
 * Created on May 11, 2016
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
