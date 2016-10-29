package com.reportolia.core.web.controllers.table;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.handler.db.SpringyDataBean;

/**
 * 
 * Handles requests for the relationship map show and load
 *
 * @author Batir Akhmerov
 * Created on Oct 23, 2016
 */
@Controller
public class RelationshipMapController {
	
	@Resource protected DbHandler dbManager;
	
	
	@RequestMapping(value = "/r3pRelationshipMapShow")
	public String relationshipMapShow(Model model) {
		return "table/relationshipMap";
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTablesSpringyDataLoad")
	public SpringyDataBean dbTablesSpringyLoad() {
		return this.dbManager.getDataModelJson();
	}
	
}
