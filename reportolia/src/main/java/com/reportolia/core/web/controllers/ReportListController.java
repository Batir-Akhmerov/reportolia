package com.reportolia.core.web.controllers;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.reportolia.core.handler.DbHandler;
import com.reportolia.core.model.table.DbTable;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ReportListController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportListController.class);
	
	@Resource protected DbHandler dbManager;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/r3pReportList", method = RequestMethod.GET)
	public String show(Locale locale, Model model) {
		logger.info("Welcome r3pReportList! The client locale is {}.", locale);
		
		//Date date = new Date();
		//DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		//String formattedDate = dateFormat.format(date);
		
		//model.addAttribute("serverTime", formattedDate );
		
		List<DbTable> list = this.dbManager.getTableList(new JTableJsonSearchForm());
		if (list.size() == 0) {
			return "redirect:/r3pTableListShow.go";
		}
		/*
		model.addAttribute("tableList", list);
		
		TestBean bean = new TestBean();
		bean.setId(1);
		bean.setName("Batir");
		bean.setStateId(2);
		model.addAttribute("command", bean);
		
		List<TestBean> stateList = new ArrayList<>();
		stateList.add(new TestBean(1, "Active", 0));
		stateList.add(new TestBean(2, "Complete", 0));
		stateList.add(new TestBean(3, "Cancelled", 0));
		
		model.addAttribute("stateList", stateList);
		*/
		
		return "reportList";
	}
	
}
