package com.reportolia.core.web.controllers.report;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reportolia.core.handler.report.ReportManager;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.model.report.ReportColumn;
import com.reportolia.core.repository.report.ReportRepository;
import com.reportolia.core.utils.CoreUtils;
import com.reportolia.core.web.controllers.base.BaseController;

/**
 * 
 * Handles requests for the show and save
 *
 * @author Batir Akhmerov
 * Created on Sep 29, 2017
 */
@Controller
public class ReportController  extends BaseController {
	
	@Resource protected ReportRepository reportRepository;
	
	@Resource protected ReportManager reportManager;
	
	@ModelAttribute("command")
	public Report getBean(Long id) {
		if (CoreUtils.isKeyNull(id)) {
			return new Report();
		}
		return this.reportRepository.findById(id);
	}
	
	@RequestMapping(value = "/r3pReportShow")
	public String show(Model model, @ModelAttribute("command") Report bean) {
		if (!bean.isNewBean()) {
			List<ReportColumn> list = this.reportManager.getReportColumns(bean);
			model.addAttribute("columnList", list);
		}
		return "report/reportBean";
	}

	
}
