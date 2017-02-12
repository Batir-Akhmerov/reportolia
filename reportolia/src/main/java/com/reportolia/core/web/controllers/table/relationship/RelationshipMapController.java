package com.reportolia.core.web.controllers.table.relationship;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reportolia.core.web.controllers.base.BaseController;

/**
 * 
 * Handles Html requests for the relationship map  
 *
 * @author Batir Akhmerov
 * Created on Oct 23, 2016
 */
@Controller
public class RelationshipMapController  extends BaseController {
	
	@RequestMapping(value = "/r3pRelationshipMapShow")
	public String relationshipMapShow(Model model) {
		return "table/relationshipMap";
	}
	
}
