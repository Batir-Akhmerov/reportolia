/**
 * 
 */
package com.reportolia.core.handler.operand;

import java.util.List;

import com.reportolia.core.model.operand.ContentType;
import com.reportolia.core.model.operand.Operand;
import com.reportolia.core.model.operand.OperandColumnPath;
import com.reportolia.core.model.operand.OperandOwnerType;

/**
 * The OperandHandler class
 *
 * @author Batir Akhmerov
 * Created on Dec 11, 2015
 */
public interface OperandHandler {
	
	public OperandOwnerType getOperandOwnerType();
	public void setOperandOwnerType(OperandOwnerType operandOwnerType);
	public ContentType getContentType();
	public void setContentType(ContentType contentType);
	
	
	
	public List<Operand> getOperandsByOwner(Long ownerId);
	
	public List<OperandColumnPath> getOperandColumnPathsByOperand(Operand operand);

}
