package com.reportolia.core.web.controllers.table.column;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.utils.CoreUtils;
import com.reportolia.core.web.controllers.base.datatable.BaseJsonController;
import com.reportolia.core.web.controllers.base.datatable.JsonDatatableResult;
import com.reportolia.core.web.controllers.base.datatable.JsonResult;

/**
 * 
 * Handles Ajax requests for table column list load, save and delete
 *
 * @author Batir Akhmerov
 * Created on Nov 23, 2016
 */
@Controller
public class TableColumnListAjaxController  extends BaseJsonController {
	
	@Resource protected DbHandler dbHandler;
	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	
	//@ModelAttribute("dbTable")
	public DbTableColumn getBean(Long id) {
		if (CoreUtils.isKeyNull(id)) {
			return null;
		}
		return this.tableColumnRepository.findById(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableColumnsLoad")
	public JsonDatatableResult<DbTableColumn> columnsLoad(TableColumnListJsonForm form, Model model) {
		return new JsonDatatableResult<DbTableColumn>(this.dbHandler.getColumnList(form), form);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableColumnSave")
	public DbTableColumn columnSave(@RequestParam("tableId") long tableId, @RequestBody DbTableColumn bean) {
		return this.dbHandler.saveColumn(bean, tableId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableColumnDelete")
	public JsonResult columnDelete(@RequestParam("tableId") long tableId, @RequestBody DbTableColumn bean) {
		this.dbHandler.deleteColumn(bean, tableId);
		return new JsonResult();
	}
	
	
}
