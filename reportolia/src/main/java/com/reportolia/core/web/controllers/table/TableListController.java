package com.reportolia.core.web.controllers.table;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.utils.CoreUtils;
import com.reportolia.core.web.controllers.base.datatable.JsonDatatableResult;
import com.reportolia.core.web.controllers.base.datatable.JsonForm;
import com.reportolia.core.web.controllers.base.datatable.JsonResult;

/**
 * 
 * Handles requests for the list show and save
 *
 * @author Batir Akhmerov
 * Created on Oct 23, 2016
 */
@Controller
public class TableListController {
	
	@Resource protected DbHandler dbHandler;
	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	
	@ModelAttribute("dbTable")
	public DbTable getBean(Long id) {
		if (CoreUtils.isKeyNull(id)) {
			return null;
		}
		return this.tableRepository.findById(id);
	}
	
	@RequestMapping(value = "/r3pTableListShow")
	public String show(Model model) {
		List<DbTable> list = this.dbHandler.getTableList(new JsonForm());
		model.addAttribute("isTableListEmpty", CollectionUtils.isEmpty(list));
		return "table/tableList";
	}

	////////////////////////////////////////////////////////////////////
	// AJAX ACTIONS ////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////

	@ResponseBody
	@RequestMapping(value = "/r3pTablesLoad")
	public JsonDatatableResult<DbTable> dbTablesLoad(JsonForm form, Model model) {
		return new JsonDatatableResult<DbTable>(this.dbHandler.getTableList(form), form);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableSave")
	public DbTable tableSave(@ModelAttribute("dbTable") DbTable bean) {
		return this.dbHandler.saveTable(bean);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableDelete")
	public JsonResult tableDelete(@ModelAttribute("dbTable") DbTable bean) {
		this.dbHandler.deleteTable(bean);
		return new JsonResult();
	}
	
	////////////////////////////////////////////////////////////////////
	// AJAX OPTIONS ////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value = "/r3pOptionsTablesLoad")
	public JsonDatatableResult<DbTable> dbOptionTablesLoad(JsonForm form, Model model) {
		return new JsonDatatableResult<DbTable>(this.dbHandler.getTableList(form), form);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/r3pOptionsTableColumnsLoad")
	public JsonDatatableResult<DbTableColumn> dbOptionTableColumnsLoad(@RequestParam Long tableId, JsonForm form, Model model) {
		return new JsonDatatableResult<DbTableColumn>(this.tableColumnRepository.findByDbTableId(tableId), form);
	}
	
	
	
	
	
}
