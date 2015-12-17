package com.reportolia.core;

import org.junit.Test;

/**
 * 
 * The Report4_StaticFilterOperands_Test class
 *
 * @author Batir Akhmerov
 * Created on Dec 11, 2015
 */
public class Report4_StaticFilterOperands_Test extends BaseReportTest{

	@Test
    public void reportTest() {
			
		testReportSql(4L, 
				" SELECT  "
						+ "tbl50.name "
						+ ",tbl50.price "
						+ ", ( SELECT TOP 1 "
							+ "_tbl50_2.quantity "
								+ "FROM products _tbl50 "
									+ "INNER JOIN orders _tbl50_2 ON _tbl50.id = _tbl50_2.product_id  "
								+ "WHERE _tbl50.id = tbl50.id "
							+ ") "
				+ "FROM products tbl50 "
				+ "WHERE tbl50.name = ? ");
		//, "Test Product");
		
    }
	
	
	
	
}
