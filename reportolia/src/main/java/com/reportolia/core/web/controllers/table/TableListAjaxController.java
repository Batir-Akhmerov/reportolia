package com.reportolia.core.web.controllers.table;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.utils.CoreUtils;
import com.reportolia.core.web.controllers.base.datatable.BaseJsonController;
import com.reportolia.core.web.controllers.base.datatable.JsonDatatableResult;
import com.reportolia.core.web.controllers.base.datatable.JsonForm;
import com.reportolia.core.web.controllers.base.datatable.JsonResult;
import com.reportolia.core.web.controllers.base.datatable.JsonSelectOptions;
import com.reportolia.core.web.controllers.base.datatable.JsonSelectOptionsForm;

/**
 * 
 * Handles Ajax requests for table list load, save and delete
 *
 * @author Batir Akhmerov
 * Created on Oct 23, 2016
 */
@Controller
public class TableListAjaxController  extends BaseJsonController {
	
	@Resource protected DbHandler dbHandler;
	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	
	//@ModelAttribute("dbTable")
	public DbTable getBean(Long id) {
		if (CoreUtils.isKeyNull(id)) {
			return null;
		}
		return this.tableRepository.findById(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTablesLoad")
	public JsonDatatableResult<DbTable> dbTablesLoad(JsonForm form, Model model) {
		return new JsonDatatableResult<DbTable>(this.dbHandler.getTableList(form), form);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableSaveAjax")
	public DbTable tableSave(@RequestBody DbTable bean) {
		return this.dbHandler.saveTable(bean);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableDelete")
	public JsonResult tableDelete(@RequestBody DbTable bean) {
		this.dbHandler.deleteTable(bean);
		return new JsonResult();
	}
	
	////////////////////////////////////////////////////////////////////
	// AJAX OPTIONS ////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value = "/r3pOptionsTablesLoad")
	public JsonSelectOptions<DbTable> dbOptionTablesLoad(JsonSelectOptionsForm form, Model model) {
		return new JsonSelectOptions<DbTable>(this.dbHandler.getTableList(form), null);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/r3pOptionsTableColumnsLoad")
	public JsonSelectOptions<DbTableColumn> dbOptionTableColumnsLoad(@RequestParam Long tableId, JsonSelectOptionsForm form, Model model) {
		return new JsonSelectOptions<DbTableColumn>(this.tableColumnRepository.findByDbTableId(tableId), null);
	}
	
	
	
	
	
}
