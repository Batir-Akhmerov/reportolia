package com.reportolia.core.web.controllers.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.reportolia.core.utils.MessageConstants;
import com.reportolia.core.utils.MessageResourceHandler;

/**
 * 
 * BaseController defines generic error handling for all page processing exceptions
 *
 * @author Batir Akhmerov
 * Created on Dec 21, 2016
 */
public class BaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected MessageResourceHandler messageResourceHandler;
	
	
	@ExceptionHandler(GoHomeException.class)
	public ModelAndView handleGoHomeException(GoHomeException ghe) {
		ModelAndView model = new ModelAndView("redirect:/r3pReportListShow.go");
		model.addObject("errorMsg", ghe.getMessage());
		return model;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(HttpServletRequest req, Exception e) {
		logger.error("\nERROR URL: " + req.getRequestURL() + " raised " + e + "\nStacktrace: " + e.getStackTrace());
		ModelAndView model = new ModelAndView("error/genericError");
		model.addObject("errorMsg", this.messageResourceHandler.get(MessageConstants.ERROR_UNEXPECTED));

		return model;

	}
	
	
}
