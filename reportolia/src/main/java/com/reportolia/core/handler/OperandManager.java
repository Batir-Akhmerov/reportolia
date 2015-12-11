/**
 * 
 */
package com.reportolia.core.handler;

import com.reportolia.core.model.operand.ContentType;
import com.reportolia.core.model.operand.OperandOwnerType;

/**
 * The QueryOperandManager class
 *
 * @author Batir Akhmerov
 * Created on Dec 11, 2015
 */
public class OperandManager implements OperandHandler {
	
	protected OperandOwnerType operandOwnerType;
	protected ContentType contentType;
	  
	public OperandOwnerType getOperandOwnerType() {
		return this.operandOwnerType;
	}
	public ContentType getContentType() {
		return this.contentType;
	}

}
