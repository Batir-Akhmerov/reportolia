/**
 * 
 */
package com.reportolia.core.handler;

import com.reportolia.core.model.operand.ContentType;
import com.reportolia.core.model.operand.OperandOwnerType;

/**
 * The QueryOperandHandler class
 *
 * @author Batir Akhmerov
 * Created on Dec 11, 2015
 */
public interface OperandHandler {
	
	public OperandOwnerType getOperandOwnerType();
	public ContentType getContentType();
	
	

}
