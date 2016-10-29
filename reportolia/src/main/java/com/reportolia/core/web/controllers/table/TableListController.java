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
import com.reportolia.core.utils.ListUtils;
import com.reportolia.core.utils.functions.Mapper;
import com.reportolia.core.web.controllers.jtable.BaseJsonResult;
import com.reportolia.core.web.controllers.jtable.JTableOption;
import com.reportolia.core.web.controllers.jtable.JsonResult;
import com.reportolia.core.web.controllers.jtable.JsonSearchForm;

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
		List<DbTable> list = this.dbHandler.getTableList(new JsonSearchForm());
		model.addAttribute("isTableListEmpty", CollectionUtils.isEmpty(list));
		return "table/tableList";
	}

	////////////////////////////////////////////////////////////////////
	// AJAX ACTIONS ////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////

	@ResponseBody
	@RequestMapping(value = "/r3pTablesLoad")
	public JsonResult<DbTable> dbTablesLoad(JsonSearchForm form, Model model) {
		return new JsonResult<DbTable>(this.dbHandler.getTableList(form));
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableSave")
	public JsonResult<DbTable> tableSave(@ModelAttribute("dbTable") DbTable bean) {
		return new JsonResult<DbTable>(this.dbHandler.saveTable(bean));
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableDelete")
	public BaseJsonResult tableDelete(@ModelAttribute("dbTable") DbTable bean) {
		this.dbHandler.deleteTable(bean);
		return new BaseJsonResult();
	}
	
	////////////////////////////////////////////////////////////////////
	// AJAX OPTIONS ////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value = "/r3pOptionsTablesLoad")
	public JsonResult<JTableOption> dbOptionTablesLoad(JsonSearchForm form, Model model) {
		List<JTableOption> list = ListUtils.remap(this.dbHandler.getTableList(form), new Mapper<DbTable, JTableOption>() {
			@Override
			public JTableOption map(DbTable bean) {
				return new JTableOption(bean.getName(), bean.getId());
			}
		});
		return new JsonResult<JTableOption>(list, true);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/r3pOptionsTableColumnsLoad")
	public JsonResult<JTableOption> dbOptionTableColumnsLoad(@RequestParam Long tableId, Model model) {
		List<JTableOption> list = ListUtils.remap(this.tableColumnRepository.findByDbTableId(tableId), new Mapper<DbTableColumn, JTableOption>() {
			@Override
			public JTableOption map(DbTableColumn bean) {
				return new JTableOption(bean.getName(), bean.getId());
			}
		});
		
		return new JsonResult<JTableOption>(list, true);
	}
	
	
	
	
	
}
