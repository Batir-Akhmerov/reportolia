package com.reportolia.core.web.controllers.jtable;

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
 * Base JTable Controller contains common methods
 *
 * @author Batir Akhmerov
 * Created on Oct 25, 2016
 */
@ControllerAdvice
public class JTableExceptionController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected MessageResourceHandler messageResourceHandler;
	
	
	@ResponseBody
	@ExceptionHandler(ValidationException.class)
	public BaseJsonResult handleValidationError(HttpServletRequest req, Exception e) {
		return new BaseJsonResult(e.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public BaseJsonResult handleError(HttpServletRequest req, Exception e) {
		logger.error("\nERROR URL: " + req.getRequestURL() + " raised " + e + "\nStacktrace: " + e.getStackTrace());
		return new BaseJsonResult(this.messageResourceHandler.get(MessageConstants.ERROR_UNEXPECTED));
	}
	
}
