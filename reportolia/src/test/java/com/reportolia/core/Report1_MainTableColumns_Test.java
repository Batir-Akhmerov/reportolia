package com.reportolia.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.reportolia.core.config.PersistenceContext;
import com.reportolia.core.handler.DbHandler;
import com.reportolia.core.handler.ReportHandler;
import com.reportolia.core.handler.ReportManager;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.repository.report.ReportColumnRepository;
import com.reportolia.core.repository.report.ReportRepository;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.sql.QueryGeneratorHandler;
import com.reportolia.core.sql.query.Query;
import com.reportolia.core.sql.query.SqlGeneratorHandler;

/**
 * 
 * The Report1_MainTableColumns_Test class
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
@DatabaseSetup("database-data.xml")
public class Report1_MainTableColumns_Test {

	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	@Resource protected DbHandler dbManager;
	@Resource protected ReportRepository reportRepository;
	@Resource protected ReportColumnRepository reportColumnRepository;
	@Resource protected ReportHandler reportManager;
	@Resource protected QueryGeneratorHandler queryGeneratorManager;
	@Resource protected SqlGeneratorHandler sqlGeneratorManager;
	
	@Test
    public void simpleReportTest() {
		Report report = this.reportRepository.findById(1L);
		
		assertThat(report.getId(), is(1L));
		
		this.reportColumnRepository.findByReport(report, ReportManager.sortByOrder);
		
		
        Query query = this.queryGeneratorManager.getReportQuery(report);
        
        List<Object> valueList = new ArrayList<>();
        String sql = this.sqlGeneratorManager.toSql(query, valueList);
        
        sql = cleanSpecialSymbols(sql);
        

        assertThat(sql, is(""));
        
    }
	
	protected String cleanSpecialSymbols(String sql) {
		sql = sql.replace('\n', ' ');
		sql = sql.replace('\t', ' ');
		sql = sql.replace("  ", " ");
		sql = sql.replace("  ", " ");
		return sql;
	}
    
	
}
