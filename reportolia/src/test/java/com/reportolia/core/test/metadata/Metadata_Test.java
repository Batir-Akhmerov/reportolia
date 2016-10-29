package com.reportolia.core.test.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.reportolia.core.driver.metadata.MetadataHandler;
import com.reportolia.core.driver.metadata.SysColumn;
import com.reportolia.core.driver.metadata.SysTable;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.utils.ListUtils;
import com.reportolia.core.web.controllers.JsonListForm;

/**
 * 
 * The Metadata_Test class
 *
 * @author Batir Akhmerov
 * Created on Dec 3, 2015
 */
public class Metadata_Test extends BaseMetadataTest {
	
	@Resource protected MetadataHandler metadataHandler;
	@Resource protected DbTableRelationshipRepository tableRelationshipRepository;
	
	
	@Test
    public void metadata_GetTables_Test() {
		SysTable tb = getSysTable(0);
		getSysColumns(tb.getName());
    }
	@Test
    public void metadata_PopulateTableRelatioshipsAndPks_Test() {
		
		// add few tables metadata into data model
		JsonListForm<SysTable> form = createJsonForm();
		
		SysTable tb = getSysTable("R3P_REPORTS");
		form.getList().add(tb);
		tb.setSelected(true);
		
		tb = getSysTable("R3P_REPORT_COLUMNS");
		form.getList().add(tb);
		tb.setSelected(true);
		
		tb = getSysTable("R3P_FOLDERS");
		form.getList().add(tb);
		tb.setSelected(true);
		
		tb = getSysTable("R3P_TABLE_COLUMNS");
		form.getList().add(tb);
		tb.setSelected(true);
		
		this.metadataHandler.saveSysTableList(form);
		
		
		// check if relationships were added
		List<DbTableRelationship> relList = this.tableRelationshipRepository.findAll();
		assertEquals(4, ListUtils.getSize(relList));
		
		List<DbTable> tbList = this.tableRepository.findAll();
		for (DbTable dbTable: tbList) {
			assertTrue(!ListUtils.isEmpty(this.tableColumnRepository.findByDbTableAndPk(dbTable, true)));
		}
		
    }
	
	
	@Test
    public void metadata_PopulateTableFull_Test() {
		// 1. select random table metadata and its columns
		SysTable tb = getSysTable("R3P_REPORTS");
		List<SysColumn> colList = getSysColumns(tb.getName());
		
		// 2. save this metadata into data model
		JsonListForm<SysTable> form = createJsonForm();
		form.getList().add(tb);
		tb.setSelected(true);
		this.metadataHandler.saveSysTableList(form);
		
		// 3. make sure Data Model contains newly added table
		List<DbTable> tbList = this.tableRepository.findAll();
		assertTrue(ListUtils.getSize(tbList) > 0);		
		DbTable dbTable = ListUtils.findByProperty(tbList, "name", tb.getName());	
		assertTrue(dbTable != null);
		assertEquals(tb.getName(), dbTable.getName());
		
		// 4. make sure Data Model contains newly added table columns
		List<DbTableColumn> tbColList = this.tableColumnRepository.findByDbTable(dbTable);
		assertTrue(ListUtils.getSize(tbColList) > 0);
		assertEquals(tbColList.size(), colList.size());
		
		// 5. request table column metadata again and make sure all sysColumns were populated
		colList = getSysColumns(tb.getName(), true);
    }
	
	@Test
    public void metadata_PopulateTablePartial_Test() {
		// 1. select another random table metadata and its columns
		SysTable tb = getSysTable("R3P_REPORT_COLUMNS");
		List<SysColumn> colList = getSysColumns(tb.getName());
		
		// 2. save this metadata into data model
		JsonListForm<SysTable> form = createJsonForm();
		form.getList().add(tb);
		tb.setSelected(true);
		List<SysColumn> partialColList = new ArrayList<>();
		tb.setColumnList(partialColList);
		// 2.5 populate only 2 columns
		SysColumn sCol = ListUtils.findByProperty(colList, "name", "ID");
		sCol.setSelected(true);
		partialColList.add(sCol);
		
		sCol = ListUtils.findByProperty(colList, "name", "REPORT_ID");
		sCol.setSelected(true);
		partialColList.add(sCol);
		
		this.metadataHandler.saveSysTableList(form);
		
		// 3. make sure Data Model contains newly added table
		List<DbTable> tbList = this.tableRepository.findAll();
		assertTrue(ListUtils.getSize(tbList) > 0);		
		DbTable dbTable = ListUtils.findByProperty(tbList, "name", tb.getName());	
		assertTrue(dbTable != null);
		assertEquals(tb.getName(), dbTable.getName());
		
		// 4. make sure Data Model contains newly added table columns
		List<DbTableColumn> tbColList = this.tableColumnRepository.findByDbTable(dbTable);
		assertTrue(ListUtils.getSize(tbColList) > 0);
		assertEquals(tbColList.size(), 2);
		
		// 5. request table column metadata again and make sure all sysColumns are disabled
		colList = getSysColumns(tb.getName());
		assertTrue(ListUtils.findByProperty(colList, "name", "ID") == null);
		assertTrue(ListUtils.findByProperty(colList, "name", "REPORT_ID") == null);
		assertTrue(ListUtils.findByProperty(colList, "name", "NAME") != null);
		assertTrue(ListUtils.findByProperty(colList, "name", "DATA_TYPE") != null);
		
    }
	
	
	
	protected SysTable getSysTable(int index) {
		List<SysTable> list = this.metadataHandler.getSysTableList();
		assertTrue(ListUtils.getSize(list) > 0);
		return list.get(index);
	}
	
	protected SysTable getSysTable(String name) {
		List<SysTable> list = this.metadataHandler.getSysTableList();
		assertTrue(ListUtils.getSize(list) > 0);
		SysTable tb = ListUtils.findByProperty(list, "name", name);
		assertTrue(tb != null);
		return tb;
	}
	
	protected List<SysColumn> getSysColumns(String tableName) {
		return getSysColumns(tableName, false);
	}
	protected List<SysColumn> getSysColumns(String tableName, boolean expectZero) {
		List<SysColumn> colList = this.metadataHandler.getSysColumnList(tableName);
		if (expectZero) {
			assertTrue(ListUtils.getSize(colList) == 0);
		}
		else {
			assertTrue(ListUtils.getSize(colList) > 0);
		}
		return colList;
	}
	
	protected JsonListForm<SysTable> createJsonForm() {
		JsonListForm<SysTable> form = new JsonListForm<>();
		form.setList(new ArrayList<SysTable>());
		return form;
	}
	
}
