package com.reportolia.core.web.controllers.base.datatable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.reportolia.core.utils.MessageConstants;
import com.reportolia.core.utils.MessageResourceHandler;

/**
 * 
 * JsonControllerAdvice defines generic error handling for datatable processing exceptions
 *
 * @author Batir Akhmerov
 * Created on Nov 24, 2016
 */
public class BaseJsonController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected MessageResourceHandler messageResourceHandler;
	
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public JsonResult handleValidationError(HttpServletRequest req, Exception e) {
		return new JsonDatatableResult<Object>(e.getMessage(), null);
	}
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public JsonResult handleError(HttpServletRequest req, Exception e) {
		logger.error("\nERROR URL: " + req.getRequestURL() + " raised " + e + "\nStacktrace: " + e.getStackTrace());
		return new JsonDatatableResult<Object>(this.messageResourceHandler.get(MessageConstants.ERROR_UNEXPECTED), null);
	}
	
	
}
