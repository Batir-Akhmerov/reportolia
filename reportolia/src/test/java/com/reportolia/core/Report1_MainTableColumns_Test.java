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
		
		testReportSql("Simple SELECT with no Joins Test", 1L, 
				" SELECT  "
						+ "tbl50.name ,tbl50.price "
				+ "FROM products tbl50 "
				+ "INNER JOIN SecurityMatrix sf_products ON tbl50.ID = sf_products.SecurityRowID AND sf_products.SectionName = 'Products' AND sf_products.UserID = 13 ");
		
		
		testReportSql( "INNER JOIN Test",
				2L, 
				" SELECT  "
						+ "tbl50.name "
						+ ",tbl50.price "
						+ ",tbl50_2.quantity "
						+ ",tbl50_2_x1.name "
				+ "FROM products tbl50 "
					+ "INNER JOIN SecurityMatrix sf_products ON tbl50.ID = sf_products.SecurityRowID AND sf_products.SectionName = 'Products' AND sf_products.UserID = 13 "
					+ "INNER JOIN orders tbl50_2 ON tbl50.id = tbl50_2.product_id "
					+ "INNER JOIN customers tbl50_2_x1 ON tbl50_2.customer_id = tbl50_2_x1.id "
					+ "INNER JOIN SecurityMatrix sf_tbl50_2_x1 ON tbl50_2.ID = sf_tbl50_2_x1.SecurityRowID AND sf_tbl50_2_x1.SectionName = 'Customers' AND sf_tbl50_2_x1.UserID = 13 ");
        
		testReportSql("Composite JOIN by 4 columns Test",
				3L, 
				" SELECT  "
						+ "tbl50.name "
						+ ",tbl50_4.quantity "
				+ "FROM products tbl50 "
				+ "INNER JOIN SecurityMatrix sf_products ON tbl50.ID = sf_products.SecurityRowID AND sf_products.SectionName = 'Products' AND sf_products.UserID = 13 "
				+ "INNER JOIN orders tbl50_4 ON tbl50.id = tbl50_4.product_id "
					+ "AND tbl50.isOutOfStockInProduct = tbl50_4.isOutOfStockInOrder "
					+ "AND tbl50.name = ? "
					+ "AND tbl50_4.status_id = ? ",
				"TEST PRODUCT", "123");
		
		testReportSql("LEFT JOIN Test", 
				4L, 
				" SELECT  "
						+ "tbl1.name "
						+ ",tbl1_1_x2.name "
						+ ",tbl1_1_x2.price "
						+ ",tbl1_1_x2_x8_x9.name "
				+ "FROM customers tbl1 "
				+ "INNER JOIN SecurityMatrix sf_customers ON tbl1.ID = sf_customers.SecurityRowID AND sf_customers.SectionName = 'Customers' AND sf_customers.UserID = 13 "
				+ "LEFT JOIN ( "
					+ "orders tbl1_1 "
					+ "INNER JOIN products tbl1_1_x2 ON tbl1_1.product_id = tbl1_1_x2.id "
					+ "INNER JOIN SecurityMatrix sf_tbl1_1_x2 ON tbl1_1.ID = sf_tbl1_1_x2.SecurityRowID AND sf_tbl1_1_x2.SectionName = 'Products' AND sf_tbl1_1_x2.UserID = 13 "
					+ "LEFT JOIN ( "
						+ "product_category tbl1_1_x2_x8 "
						+ "INNER JOIN product_category_type tbl1_1_x2_x8_x9 ON tbl1_1_x2_x8.type_id = tbl1_1_x2_x8_x9.id "
					+ ") ON tbl1_1_x2.category_id = tbl1_1_x2_x8.id "
				+ ") ON tbl1.id = tbl1_1.customer_id ");
		
    }
	
	
	
	
}
