package com.reportolia.core.web.controllers.report;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.web.controllers.base.BaseController;

/**
 * 
 * Handles requests for the application home page and shows a report list page
 *
 * @author Batir Akhmerov
 * Created on Oct 23, 2016
 */
@Controller
public class ReportListController  extends BaseController {
	
	//private static final Logger logger = LoggerFactory.getLogger(ReportListController.class);
	
	//@Resource protected DbHandler dbManager;
	@Resource protected DbTableRepository tableRepository;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/r3pReportListShow", method = RequestMethod.GET)
	public String show(Model model) {
		
		//Date date = new Date();
		//DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		//String formattedDate = dateFormat.format(date);
		
		//model.addAttribute("serverTime", formattedDate );
		
		if (this.tableRepository.count() == 0) {
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
		
		return "report/reportList";
	}
	
}
