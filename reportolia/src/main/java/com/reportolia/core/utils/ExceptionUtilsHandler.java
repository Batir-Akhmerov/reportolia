/**
 * 
 */
package com.reportolia.core.utils;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * The ExceptionUtilsHandler class
 *
 * @author Batir Akhmerov
 * Created on Oct 28, 2016
 */
@Component
public class ExceptionUtilsHandler {
	
	public static final String MSG_VALIDATION_ERROR = "Validation Error!";
	
	@Resource protected MessageResourceHandler messageResourceHandler;

	public void validateRequired(String value, String fieldNameMesageKey) {
		if (!StringUtils.isEmpty(value)) {
			return;
		}
		String fName = this.messageResourceHandler.get(fieldNameMesageKey);
		if (StringUtils.isEmpty(fName)) {
			fName = fieldNameMesageKey;
		}
		invalid(MessageConstants.ERROR_REQUIRED_FIELD, fName);
	}
	
	public void validateFalse(boolean validIfFalse, String mesageKey, Object...args) {
		if (!validIfFalse) {
			return;
		}
		invalid(mesageKey, args);
	}
	
	public void validateEmpty(List<?> list, String mesageKey, Object...args) {
		if (ListUtils.isEmpty(list)) {
			return;
		}
		invalid(mesageKey, args);
	}
	
	public void validateNotEmpty(List<?> list, String mesageKey, Object...args) {
		if (!ListUtils.isEmpty(list)) {
			return;
		}
		invalid(mesageKey, args);
	}
	
	public void invalid(String mesageKey, Object...args) {
		String error = this.messageResourceHandler.get(mesageKey, args);
		throw new ValidationException(error);
	}
	
	/*
	public void validateInvalid(boolean invalidCond, String msg, Object ... args) {
		if (invalidCond) {
			String error = StringUtils.isEmpty(msg) ? MSG_VALIDATION_ERROR : format(msg, args);
			throw new ValidationException(error);
		}
	}
	*/
	public void assertFalse(boolean invalidCond, String msg, Object ... args) {
		if (invalidCond) {
			String error = StringUtils.isEmpty(msg) ? MSG_VALIDATION_ERROR : CoreUtils.format(msg, args);
			throw new IllegalArgumentException(error);
		}
	}
}
	