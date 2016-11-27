package com.reportolia.core.web.controllers.metadata;

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
import com.reportolia.core.web.controllers.JsonListForm;
import com.reportolia.core.web.controllers.base.datatable.JsonDatatableResult;
import com.reportolia.core.web.controllers.base.datatable.JsonForm;

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
	public JsonDatatableResult<SysTable> sysTablesLoad(JsonForm form, Model model) {
		return new JsonDatatableResult<SysTable>(this.metadataHandler.getSysTableList(), form);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/r3pDbMetadataColumnsLoad")
	public JsonDatatableResult<SysColumn> sysColumnsLoad(String tableName, JsonForm form, Model model) {
		return new JsonDatatableResult<SysColumn>(this.metadataHandler.getSysColumnList(tableName), form);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pDbMetadataSave")
	public JsonDatatableResult<Object> sysTablesSave(@RequestBody final JsonListForm<SysTable> json) {
		this.metadataHandler.saveSysTableList(json);
		return new JsonDatatableResult<Object>();
	}
	
	
}
