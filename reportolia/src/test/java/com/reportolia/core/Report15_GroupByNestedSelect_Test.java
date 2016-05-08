package com.reportolia.core;

import org.junit.Test;

/**
 * 
 * The Report15_GroupByNestedSelect_Test class
 *
 * @author Batir Akhmerov
 * Created on Feb 18, 2016
 */
public class Report15_GroupByNestedSelect_Test extends BaseReportTest{

	@Test
    public void simpleReportTest() {
		
		testReportSql("GROUP BY in NESTED SELECT", 15L, 
				" SELECT  "
				+ "tbl1.name , "
				+ "( SELECT TOP 1 "
						+ "_tbl1_1_x2.name "
						+ "+ CAST( ( "
								+ "SUM( _tbl1_1_x2.price )"
						+ " ) AS VARCHAR(100)) "
					+ "FROM orders _tbl1_1 "
						+ "INNER JOIN products _tbl1_1_x2 ON _tbl1_1.product_id = _tbl1_1_x2.id  "
					+ "WHERE _tbl1_1.customer_id = tbl1.id "
					+ "GROUP BY _tbl1_1_x2.name "
				+ ") "
				+ "FROM customers tbl1 ");
		
    }
	
	
	
	
}
