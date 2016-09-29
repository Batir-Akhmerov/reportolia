package com.reportolia.core;

import org.junit.Test;

/**
 * 
 * The Report51_ParameterizedNestedSelect_Test class
 *
 * @author Batir Akhmerov
 * Created on Sept 2, 2016
 */
public class Report51_ParameterizedNestedSelect_Test extends BaseReportTest{

	@Test
    public void reportTest() {
			
		testReportSql("Parameterized Nested Select",
			51L,
			" SELECT  ( "
					+ "SELECT TOP 1 "
						+ "SUM( _tbl1_1_x2.price ) "
					+ "FROM orders _tbl1_1 "
						+ "INNER JOIN products _tbl1_1_x2 ON _tbl1_1.product_id = _tbl1_1_x2.id "
						+ "INNER JOIN order_status _tbl1_1_x3 ON _tbl1_1.status_id = _tbl1_1_x3.id  "
					+ "WHERE _tbl1_1_x3.name = ? "
						+ "AND _tbl1_1.customer_id = tbl1.id "
					+ "GROUP BY _tbl1_1_x3.name "
			+ ") "
			+ "FROM customers tbl1 ", "Active");
		
    }
	
	
	
	
}
