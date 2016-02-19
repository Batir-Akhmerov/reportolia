package com.reportolia.core;

import org.junit.Test;

/**
 * 
 * The Report1_MainTableColumns_Test class
 *
 * @author Batir Akhmerov
 * Created on Dec 3, 2015
 */
public class Report15_GroupByNestedSelect_Test extends BaseReportTest{

	@Test
    public void simpleReportTest() {
		
		testReportSql("GROUP BY in NESTED SELECT", 15L, 
				" SELECT  "
				+ "tbl1.name , "
				+ "( SELECT TOP 1 "
						+ "_tbl1_1_x2.name "
						+ "+ SUM( _tbl1_1_x2.price ) "
					+ "FROM orders _tbl1_1 "
						+ "INNER JOIN products _tbl1_1_x2 ON _tbl1_1.product_id = _tbl1_1_x2.id  "
					+ "WHERE _tbl1_1.customer_id = tbl1.id "
					+ "GROUP BY _tbl1_1_x2.name "
				+ ") "
				+ "FROM customers tbl1 ");
		
		/**
		 * TODO:
		 * 1) Put tbl1.id into topCommand.grouByList if topLevelQuery is aggregated (along with tbl1.name)
		 * 2) Put the whole Simple Calc Column into groupBy if it doesn't contain aggregates and owner query is aggregated
		 * 3)
		 * 
		 */
		
		
    }
	
	
	
	
}
