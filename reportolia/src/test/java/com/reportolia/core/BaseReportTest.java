package com.reportolia.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
import com.reportolia.core.config.ColumnDetectorXmlDataSetLoader;
import com.reportolia.core.config.PersistenceContext;
import com.reportolia.core.handler.DbHandler;
import com.reportolia.core.handler.ReportHandler;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.repository.report.ReportColumnRepository;
import com.reportolia.core.repository.report.ReportRepository;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.sql.QueryGeneratorHandler;
import com.reportolia.core.sql.ReportQueryGeneratorHandler;
import com.reportolia.core.sql.query.Query;
import com.reportolia.core.sql.query.SqlGeneratorHandler;

/**
 * 
 * The BaseReportTest class
 *
 * @author Batir Akhmerov
 * Created on Dec 3, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContext.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ColumnDetectorXmlDataSetLoader.class)
@DatabaseSetup("database-data.xml")
public class BaseReportTest {

	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	@Resource protected DbHandler dbManager;
	@Resource protected ReportRepository reportRepository;
	@Resource protected ReportColumnRepository reportColumnRepository;
	@Resource protected ReportHandler reportManager;
	@Resource protected QueryGeneratorHandler queryGeneratorManager;
	@Resource protected ReportQueryGeneratorHandler reportQueryGeneratorManager;
	@Resource protected SqlGeneratorHandler sqlGeneratorManager;
	
	protected void testReportSql(Long reportId, String expectedSql) {
		List<Object> valueList = new ArrayList<>();
		testReportSql(reportId, expectedSql, valueList); 
	}
	
	protected void testReportSql(Long reportId, String expectedSql, Object... expectedValues) {
		List<Object> valueList = new ArrayList<>();
		testReportSql(reportId, expectedSql, valueList); 
		if (expectedValues != null) {
			assertEquals("Value list size should match!", expectedValues.length, valueList.size());
			int i = 0;
			for (Object v: expectedValues) {
				assertEquals("Sql Value should match!", v, valueList.get(i++));
			}
		}
	}
	
    protected void testReportSql(Long reportId, String expectedSql, List<Object> valueList) {
		Report report = this.reportRepository.findById(reportId);
		
        Query query = this.reportQueryGeneratorManager.getReportQuery(report);
        
        String sql = this.sqlGeneratorManager.toSql(query, valueList);
        
        sql = cleanSpecialSymbols(sql);
        

        assertEquals(expectedSql, sql);
        
    }
	
	protected String cleanSpecialSymbols(String sql) {
		sql = sql.replace('\n', ' ');
		sql = sql.replace('\t', ' ');
		sql = sql.replace("  ", " ");
		sql = sql.replace("  ", " ");
		return sql;
	}
    
	
}
