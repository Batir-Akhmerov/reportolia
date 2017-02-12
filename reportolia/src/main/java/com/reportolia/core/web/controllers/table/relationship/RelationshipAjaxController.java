package com.reportolia.core.web.controllers.table.relationship;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.handler.db.relationship.RelationshipInfo;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.web.controllers.base.datatable.BaseJsonController;
import com.reportolia.core.web.controllers.base.datatable.JsonDatatableResult;
import com.reportolia.core.web.controllers.base.datatable.JsonForm;
import com.reportolia.core.web.controllers.base.datatable.JsonResult;

/**
 * 
 * Handles Ajax requests for the relationship bean load, save and delete
 *
 * @author Batir Akhmerov
 * Created on Oct 23, 2016
 */
@Controller
public class RelationshipAjaxController extends BaseJsonController {
	
	@Resource protected DbHandler dbHandler;
	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableRelationshipRepository tableRelationshipRepository;
	
	/*
	@RequestMapping(value = "/r3pTableRelationshipsShow")
	public ModelAndView relationshipsShow(long tableId, ModelAndView mav) {
		mav.setViewName("table/relationshipList");
		mav.getModel().put("currentTable", this.tableRepository.findById(tableId));
		return mav;
	}
	*/
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableRelationshipsLoad")
	public JsonDatatableResult<RelationshipInfo> relationshipsLoad(long tableId, JsonForm form) {
		return new JsonDatatableResult<RelationshipInfo>(this.dbHandler.getTableRelationshipInfoList(tableId), form);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableRelationshipSave")
	public RelationshipInfo relationshipSave(@RequestBody RelationshipInfo info) {
		return this.dbHandler.saveTableRelationship(info);
	}
	
	@ResponseBody
	@RequestMapping(value = "/r3pTableRelationshipDelete")
	public JsonResult relationshipDelete(@RequestBody RelationshipInfo info) {
		this.dbHandler.deleteTableRelationship(info);
		return new JsonResult();
	}
	
}
