package com.reportolia.core.web.controllers.table.column;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.utils.CoreUtils;
import com.reportolia.core.web.controllers.base.BaseController;
import com.reportolia.core.web.controllers.base.GoHomeException;

/**
 * 
 * Handles requests for the list show and save
 *
 * @author Batir Akhmerov
 * Created on Oct 23, 2016
 */
@Controller
public class TableColumnListController  extends BaseController {
	
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
	
	@RequestMapping(value = "/r3pTableColumnListShow")
	public String show(Model model, @ModelAttribute("dbTable") DbTable dbTable) {		
		if (dbTable == null) {
			throw new GoHomeException();
		}
		return "table/columns/columnList";
	}

	
}
