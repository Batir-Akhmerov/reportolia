package com.reportolia.core;

import org.junit.Test;

/**
 * 
 * The Report16_GroupByMainQuery_Test class
 *
 * @author Batir Akhmerov
 * Created on Feb 19, 2016
 */
public class Report16_GroupByMainQuery_Test extends BaseReportTest{

	@Test
    public void reportTest() {
		
		testReportSql("GROUP BY in Main Report Query", 16L, 
				" SELECT  "
						+ "tbl1.name "
						+ ",CAST( ( SUM( tbl1_1_x2.price ) ) AS VARCHAR(100)) + tbl1_1_x2.name "
						+ ", ( SELECT TOP 1 "
									+ "_tbl1_1_x2_2.quantity "
								+ "FROM orders _tbl1_1_x2_2 "
								+ "WHERE _tbl1_1_x2_2.product_id = tbl1_1_x2.id "
						+ ") "
						+ ", ( tbl1.name + CAST( ( tbl1.id ) AS VARCHAR(100)) ) "
				+ "FROM customers tbl1 "
					+ "LEFT JOIN ( "
						+ "orders tbl1_1 "
						+ "INNER JOIN products tbl1_1_x2 ON tbl1_1.product_id = tbl1_1_x2.id "
					+ ") ON tbl1.id = tbl1_1.customer_id "
				+ " GROUP BY "
						+ "tbl1.name "
						+ ",tbl1_1_x2.name "
						+ ",tbl1_1_x2.id "
						+ ",tbl1.name + CAST( ( tbl1.id ) AS VARCHAR(100)) ");
		
    }
	
	
	
	
}
