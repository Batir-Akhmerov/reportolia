package com.reportolia.core.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.handler.report.ReportManager;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.repository.report.ReportColumnRepository;
import com.reportolia.core.repository.report.ReportRepository;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.sql.ReportQueryGeneratorHandler;
import com.reportolia.core.sql.SqlGeneratorHandler;
import com.reportolia.core.sql.query.QueryGeneratorHandler;
import com.reportolia.core.sql.query.model.Query;
import com.reportolia.core.sql.query.model.SecurityType;
import com.reportolia.core.test.config.ColumnDetectorXmlDataSetLoader;

/**
 * 
 * The BaseMetadataTest class
 *
 * @author Batir Akhmerov
 * Created on Dec 3, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {PersistenceContext.class})
@ContextConfiguration(locations = {"classpath:testContext-persistence.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ColumnDetectorXmlDataSetLoader.class)
@DatabaseSetup("classpath:database-data.xml")
@Ignore
public class BaseReportTest {
	
	private SecurityType securityType = SecurityType.NONE; 

	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	@Resource protected DbHandler dbHandler;
	@Resource protected ReportRepository reportRepository;
	@Resource protected ReportColumnRepository reportColumnRepository;
	@Resource protected ReportManager reportManager;
	@Resource protected QueryGeneratorHandler queryGeneratorManager;
	@Resource protected ReportQueryGeneratorHandler reportQueryGeneratorManager;
	@Resource protected SqlGeneratorHandler sqlGeneratorManager;
	/*
	@Test
    public void reportTest() {
		
	}
	*/
	protected void testReportSql(String testName, Long reportId, String expectedSql) {
		List<Object> valueList = new ArrayList<>();
		testReportSql(testName, reportId, expectedSql, valueList); 
	}
	
	protected void testReportSql(String testName, Long reportId, String expectedSql, Object... expectedValues) {
		List<Object> valueList = new ArrayList<>();
		testReportSql(testName, reportId, expectedSql, valueList); 
		if (expectedValues != null) {
			assertEquals(testName + ". Message: Value list size should match!", expectedValues.length, valueList.size());
			int i = 0;
			for (Object v: expectedValues) {
				assertEquals(testName + ". Message: Sql Value should match!", v, valueList.get(i++));
			}
		}
	}
	
    protected void testReportSql(String testName, Long reportId, String expectedSql, List<Object> valueList) {
		Report report = this.reportRepository.findById(reportId);
		Query query = new Query(getSecurityType());
        query = this.reportQueryGeneratorManager.getReportQuery(report, query);
        
        String sql = this.sqlGeneratorManager.toSql(query, valueList);
        
        sql = cleanSpecialSymbols(sql);
        

        assertEquals(testName + "!", expectedSql, sql);
        
    }
	
	protected String cleanSpecialSymbols(String sql) {
		sql = sql.replace('\n', ' ');
		sql = sql.replace('\t', ' ');
		sql = sql.replace("  ", " ");
		sql = sql.replace("  ", " ");
		return sql;
	}

	public SecurityType getSecurityType() {
		return this.securityType;
	}

	public void setSecurityType(SecurityType securityType) {
		this.securityType = securityType;
	}
    
	
}
