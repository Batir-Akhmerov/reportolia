package com.reportolia.core;

import org.junit.Test;

/**
 * 
 * The Report1_MainTableColumns_Test class
 *
 * @author Batir Akhmerov
 * Created on Dec 3, 2015
 */
public class Report1_MainTableColumns_Test extends BaseReportTest{

	@Test
    public void simpleReportTest() {
			
		testReportSql(1L, " SELECT  tbl50.name ,tbl50.price FROM products tbl50 ");
		
		testReportSql(2L, " SELECT  tbl50.name ,tbl50.price ,tbl50_2.quantity ,tbl50_x1.name "
				+ "FROM products tbl50 "
				+ "INNER JOIN orders tbl50_2 ON tbl50.id = tbl50_2.product_id "
				+ "INNER JOIN customers tbl50_x1 ON tbl50_2.customer_id = tbl50_x1.id ");
        
		
		testReportSql(3L, " SELECT  tbl50.name ,tbl50_4.quantity "
				+ "FROM products tbl50 "
				+ "INNER JOIN orders tbl50_4 ON tbl50.id = tbl50_4.product_id "
				+ "AND tbl50.isOutOfStockInProduct = tbl50_4.isOutOfStockInOrder "
				+ "AND tbl50.name = ? "
				+ "AND tbl50_4.status_id = ? ",
				"TEST PRODUCT", "123");
    }
	
	
	
	
}
