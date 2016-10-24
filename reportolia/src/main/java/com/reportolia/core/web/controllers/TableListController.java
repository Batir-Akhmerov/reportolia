package com.reportolia.core.web.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.handler.DbHandler;
import com.reportolia.core.model.table.DbTable;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TableListController {
	
	@Resource protected DbHandler dbManager;
	
	
	@RequestMapping(value = "/r3pTableListShow")
	public String show(Model model) {
		List<DbTable> list = this.dbManager.getTableList(new JTableJsonSearchForm());
		model.addAttribute("isTableListEmpty", CollectionUtils.isEmpty(list));
		return "table/tableList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTablesLoad")
	public JTableJsonResult<DbTable> dbTablesLoad(JTableJsonSearchForm form, Model model) {
		return new JTableJsonResult<DbTable>(this.dbManager.getTableList(form));
	}
	/*
	@RequestMapping(value = "/r3pTableListFromDbShow")
	public String dbTablesShow(Model model) {
		
		List<SysTable> list = this.sqlExecutorHandler.getSysTableList();
		model.addAttribute("sysTableList", list);
		
		return "db_schema/sysTableList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pSysTablesLoad")
	public List<SysTable> sysTablesLoad(Model model) {
		return this.sqlExecutorHandler.getSysTableList();
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableListFromDbSave")
	public JTableJsonResult<Object> dbTablesSave(@RequestBody final JsonListForm<SysTable> json) {
		this.sqlExecutorHandler.saveSysTableList(json);
		return new JTableJsonResult<Object>();
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableFromDbSave")//, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String dbTables(@RequestBody final SysTable json) {
		//this.sqlExecutorHandler.saveSysTableList(json);
		return null;
	}
	*/
	/*
	 @ResponseBody
@RequestMapping(value = "/account/create", method = RequestMethod.POST)
public String createAccount(@RequestBody final Account account) {
    System.out.println("name = " + account.getName());
    System.out.println("type = " + account.getType());
    System.out.println("amount = " + account.getAmount());      
    return null;
}
	  
	@ResponseBody
	@RequestMapping(value = "/r3pTableListFromDbSave")
	public AjaxResponseBody dbTablesSave(@RequestBody JsonListForm<SysTable> json, Model model) {
		this.sqlExecutorHandler.saveSysTableList(json);
		return new AjaxResponseBody();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/search/api/getSearchResult")
	public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {

		AjaxResponseBody result = new AjaxResponseBody();
		//logic
		return result;

	}
	*/
}
