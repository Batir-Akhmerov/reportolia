package com.reportolia.core.web.controllers.report;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.handler.report.ReportManager;
import com.reportolia.core.model.report.Report;
import com.reportolia.core.web.controllers.base.datatable.BaseJsonController;
import com.reportolia.core.web.controllers.base.datatable.JsonDatatableResult;
import com.reportolia.core.web.controllers.base.datatable.JsonResult;

/**
 * 
 * Handles Ajax requests for Report list load and delete
 *
 * @author Batir Akhmerov
 * Created on Jan 2, 2017
 */
@Controller
public class ReportListAjaxController  extends BaseJsonController {
	
	@Resource protected ReportManager reportManager;
		
	@ResponseBody
	@RequestMapping(value = "/r3pReportsLoad")
	public JsonDatatableResult<Report> oad(ReportListJsonForm form, Model model) {
		return new JsonDatatableResult<Report>(this.reportManager.getReportList(form), form);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/r3pReportDelete")
	public JsonResult delete(@RequestBody Long reportId) {
		this.reportManager.deleteReport(reportId);
		return new JsonResult();
	}
	
	
	
}
