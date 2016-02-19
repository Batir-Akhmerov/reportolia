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
						+ ",SUM( tbl1_1_x2.price ) + tbl1_1_x2.name "
				+ "FROM customers tbl1 "
					+ "LEFT JOIN ( "
						+ "orders tbl1_1 "
						+ "INNER JOIN products tbl1_1_x2 ON tbl1_1.product_id = tbl1_1_x2.id "
					+ ") ON tbl1.id = tbl1_1.customer_id "
				+ " GROUP BY "
						+ "tbl1.name "
						+ ",tbl1_1_x2.name ");
		
		/**
		 * TODO:
		 * 1) Put tbl1.id into topCommand.grouByList if topLevelQuery is aggregated (along with tbl1.name)
		 * 2) Put the whole Simple Calc Column into groupBy if it doesn't contain aggregates and owner query is aggregated
		 * 3)
		 * 
		 */
		
		
    }
	
	
	
	
}
