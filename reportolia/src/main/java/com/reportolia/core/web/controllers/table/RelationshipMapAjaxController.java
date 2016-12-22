package com.reportolia.core.web.controllers.table;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.handler.db.SpringyDataBean;
import com.reportolia.core.web.controllers.base.datatable.BaseJsonController;

/**
 * 
 * Handles Ajax requests for the relationship map 
 *
 * @author Batir Akhmerov
 * Created on Dec 21, 2016
 */
@Controller
public class RelationshipMapAjaxController  extends BaseJsonController {
	
	@Resource protected DbHandler dbManager;
	
	
	@ResponseBody
	@RequestMapping(value = "/r3pTablesSpringyDataLoad")
	public SpringyDataBean dbTablesSpringyLoad() {
		return this.dbManager.getDataModelJson();
	}
	
}
