package com.reportolia.core.web.controllers.base.datatable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reportolia.core.utils.MessageConstants;
import com.reportolia.core.utils.MessageResourceHandler;

/**
 * 
 * JsonControllerAdvice defines generic error handling for datatable processing exceptions
 *
 * @author Batir Akhmerov
 * Created on Nov 24, 2016
 */
@ControllerAdvice
public class JsonControllerAdvice {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected MessageResourceHandler messageResourceHandler;
	
	
	@ResponseBody
	@ExceptionHandler(ValidationException.class)
	public JsonResult handleValidationError(HttpServletRequest req, Exception e, JsonForm form) {
		return new JsonResult(e.getMessage(), form);
	}
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public JsonResult handleError(HttpServletRequest req, Exception e, JsonForm form) {
		logger.error("\nERROR URL: " + req.getRequestURL() + " raised " + e + "\nStacktrace: " + e.getStackTrace());
		return new JsonResult(this.messageResourceHandler.get(MessageConstants.ERROR_UNEXPECTED), form);
	}
	
}
