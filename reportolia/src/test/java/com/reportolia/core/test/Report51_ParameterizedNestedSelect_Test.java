package com.reportolia.core.test;

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
			" SELECT  ( " // report column linked to table column
					+ "SELECT TOP 1 "
						+ "SUM( _tbl1_1_x2.price ) "
					+ "FROM orders _tbl1_1 "
						+ "INNER JOIN products _tbl1_1_x2 ON _tbl1_1.product_id = _tbl1_1_x2.id "
						+ "INNER JOIN order_status _tbl1_1_x3 ON _tbl1_1.status_id = _tbl1_1_x3.id  "
					+ "WHERE _tbl1_1_x3.name = ? "
						+ "AND _tbl1_1.customer_id = tbl1.id "
					+ "GROUP BY _tbl1_1_x3.name "
			+ ") "
			+ ", ( SELECT TOP 1 " // report calculated column with table column linked to operand
						+ "SUM( _tbl1_1_x2.price ) "
					+ "FROM orders _tbl1_1 "
						+ "INNER JOIN products _tbl1_1_x2 ON _tbl1_1.product_id = _tbl1_1_x2.id "
						+ "INNER JOIN order_status _tbl1_1_x3 ON _tbl1_1.status_id = _tbl1_1_x3.id  "
					+ "WHERE _tbl1_1_x3.name = ? "
						+ "AND _tbl1_1.customer_id = tbl1.id "
					+ "GROUP BY _tbl1_1_x3.name "
				+ ")"
				+ " + ? "
			+ "FROM customers tbl1 "
			+ "WHERE "
				+ "( SELECT TOP 1 " // report static filter operand
							+ "SUM( _tbl1_1_x2.price ) "
						+ "FROM orders _tbl1_1 "
							+ "INNER JOIN products _tbl1_1_x2 ON _tbl1_1.product_id = _tbl1_1_x2.id "
							+ "INNER JOIN order_status _tbl1_1_x3 ON _tbl1_1.status_id = _tbl1_1_x3.id  "
						+ "WHERE _tbl1_1_x3.name = ? "
							+ "AND _tbl1_1.customer_id = tbl1.id "
						+ "GROUP BY _tbl1_1_x3.name "
					+ ") = ? ", 
			"Variable Value Report with Table Calculated Column",
			"Variable Value Report Calculated Column Operand",
			"Free Form Value",
			"Variable Value Report Filter Operand",
			"Test Filter Value");
		
    }
	
	
	
	
}
