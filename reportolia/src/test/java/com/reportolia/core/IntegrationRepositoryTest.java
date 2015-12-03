package com.reportolia.core;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.sql.QueryGeneratorHandler;

/**
 * 
 * The IntegrationRepositoryTest class
 *
 * @author Batir Akhmerov
 * Created on Dec 3, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContext.class})
//@ContextConfiguration(locations = {"classpath:ExampleConfigurationTests-context.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
//@DatabaseSetup("/com/reportolia/core/toDoData1.xml")
@DatabaseSetup("database-data.xml")
public class IntegrationRepositoryTest {

	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	@Resource protected DbHandler dbManager;
	@Resource protected QueryGeneratorHandler queryGeneratorManager;
	
	@Test
    public void findTableByName_OneTodoItemEntryFound_ShouldReturnAListOfOneEntry2() {
        List<DbTable> list = queryGeneratorManager.getDbTableList("customers");

        assertThat(list.size(), is(1));
        DbTable table = list.get(0);
        assertThat(table, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("customers"))
        ));
        
        assertThat(table.getDbTableColumns().size(), is(2));
        
        DbTableColumn col = table.getDbTableColumns().get(0);
        assertThat(col, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("id")),
                hasProperty("label", is("customer id"))
        ));
        
        
        col = table.getDbTableColumns().get(1);
        assertThat(col, allOf(
                hasProperty("id", is(2L)),
                hasProperty("name", is("name")),
                hasProperty("label", is("customer name"))
        ));
        
        this.tableColumnRepository.findAll();
        this.tableColumnRepository.findByDbTable(table);
        
        List<DbTableRelationship> relList = queryGeneratorManager.getDbTableChildRelationshipList(table.getId());
        assertThat(relList.size(), is(1));
        DbTableRelationship relBean = relList.get(0);
        assertThat(relBean, 
                hasProperty("id", is(1L))/*,
                hasProperty("dbColumnParent.id", is(1L)),
                hasProperty("dbColumnChild.id", is(101L))*/
        );
        
        assertThat(relBean.getDbColumnParent(), hasProperty("id", is(1L)));
        assertThat(relBean.getDbColumnChild(), hasProperty("id", is(101L)));
    }
    
	/*
    @Test
    public void findTableByName_OneTodoItemEntryFound_ShouldReturnAListOfOneEntry1() {
        List<DbTable> todoEntries = dbManager.getDbTableList("milk");

        assertThat(todoEntries.size(), is(1));
        DbTable table = todoEntries.get(0);
        assertThat(table, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("milk")),
                hasProperty("description", is("white"))
        ));
        
        assertThat(table.getDbTableColumns().size(), is(1));
        DbTableColumn col = table.getDbTableColumns().iterator().next();
        assertThat(col, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("column milk")),
                hasProperty("label", is("column milk"))
        ));
    }
    

    @Test
    public void findTableByName_OneTodoItemEntryFound_ShouldReturnAListOfOneEntry() {
        List<DbTable> todoEntries = tableRepository.findByName("milk");

        assertThat(todoEntries.size(), is(1));
        DbTable table = todoEntries.get(0);
        assertThat(table, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("milk")),
                hasProperty("description", is("white"))
        ));
        
        assertThat(table.getDbTableColumns().size(), is(1));
        DbTableColumn col = table.getDbTableColumns().iterator().next();
        assertThat(col, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("column milk")),
                hasProperty("label", is("column milk"))
        ));
    }
    
    @Test
    public void findCOlumnByName_OneTodoItemEntryFound_ShouldReturnAListOfOneEntry() {
        List<DbTableColumn> todoEntries = tableColumnRepository.findByName("column milk");

        assertThat(todoEntries.size(), is(1));
        
        DbTableColumn col = todoEntries.get(0);
        assertThat(col, allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("column milk")),
                hasProperty("label", is("column milk"))
        ));
        
        assertThat(col.getDbTable(), allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("milk")),
                hasProperty("description", is("white"))
        ));
        
    }
  */
    /*
    @Test
    public void search_NoTodoEntriesFound_ShouldReturnEmptyList() {
        List<Todo> todoEntries = repository.search("NOT FOUND");
        assertThat(todoEntries.size(), is(0));
    }

    @Test
    public void search_OneTodoEntryFound_ShouldReturnAListOfOneEntry() {
        List<Todo> todoEntries = repository.search("foo");

        assertThat(todoEntries.size(), is(1));
        assertThat(todoEntries.get(0), allOf(
                hasProperty("id", is(1L)),
                hasProperty("title", is("Foo")),
                hasProperty("description", is("Lorem ipsum"))
        ));
    }

    @Test
    public void search_TwoTodoEntriesFound_ShouldReturnAListOfTwoEntries() {
        List<Todo> todoEntries = repository.search("Ips");

        assertThat(todoEntries.size(), is(2));
        assertThat(todoEntries, contains(
                allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("title", is("Foo")),
                        hasProperty("description", is("Lorem ipsum"))
                ),
                allOf(
                        hasProperty("id", is(2L)),
                        hasProperty("title", is("Bar")),
                        hasProperty("description", is("Lorem ipsum"))
                )
        ));
    }
    */
}
