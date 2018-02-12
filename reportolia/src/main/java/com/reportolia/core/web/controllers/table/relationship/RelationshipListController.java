package com.reportolia.core.web.controllers.table.relationship;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.utils.CoreUtils;
import com.reportolia.core.web.controllers.base.datatable.BaseJsonController;

/**
 * 
 * RelationshipAjaxController - responsible for showing all Table Relatioships 
 *
 * @author Batir Akhmerov
 * Created on Jan 19, 2018
 */
@Controller
public class RelationshipListController extends BaseJsonController {
	
	@Resource protected DbHandler dbHandler;
	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableRelationshipRepository tableRelationshipRepository;
	
	@ModelAttribute("dbTable")
	public DbTable getBean(Long id) {
		if (CoreUtils.isKeyNull(id)) {
			return new DbTable();
		}
		return this.tableRepository.findById(id);
	}
	
	
	@RequestMapping(value = "/r3pTableRelationshipsShow")
	public ModelAndView relationshipsShow(@ModelAttribute("dbTable") DbTable dbTable, ModelAndView mav) {
		mav.setViewName("table/relationshipList");		
		return mav;
	}
	
	
	
}
