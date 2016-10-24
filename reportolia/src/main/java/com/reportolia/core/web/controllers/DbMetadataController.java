package com.reportolia.core.web.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.driver.metadata.MetadataHandler;
import com.reportolia.core.driver.metadata.SysColumn;
import com.reportolia.core.driver.metadata.SysTable;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DbMetadataController {
	
	@Resource protected MetadataHandler metadataHandler;
	
	
	@RequestMapping(value = "/r3pDbMetadataShow")
	public String sysTablesShow(Model model) {
		List<SysTable> list = this.metadataHandler.getSysTableList();
		model.addAttribute("sysTableList", list);
		return "metadata/metadataList";
	}
	
	@ResponseBody	
	@RequestMapping(value = "/r3pDbMetadataLoad")
	public JTableJsonResult<SysTable> sysTablesLoad(JTableJsonSearchForm form, Model model) {
		return new JTableJsonResult<SysTable>(this.metadataHandler.getSysTableList());
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pDbMetadataColumnsLoad")
	public JTableJsonResult<SysColumn> sysColumnsLoad(String tableName, JTableJsonSearchForm form, Model model) {
		return new JTableJsonResult<SysColumn>(this.metadataHandler.getSysColumnList(tableName));
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pDbMetadataSave")
	public JTableJsonResult<Object> sysTablesSave(@RequestBody final JsonListForm<SysTable> json) {
		this.metadataHandler.saveSysTableList(json);
		return new JTableJsonResult<Object>();
	}
	/*
	@ResponseBody
	@RequestMapping(value = "/r3pTableFromDbSave")//, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String dbTables(@RequestBody final SysTable json) {
		//this.sqlExecutorHandler.saveSysTableList(json);
		return null;
	}
	*/
	
}
