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
			
		testReportSql(4L, " SELECT  tbl50.name ,tbl50.price "
				+ "FROM products tbl50 "
		, "Test Product");
		
    }
	
	
	
	
}
