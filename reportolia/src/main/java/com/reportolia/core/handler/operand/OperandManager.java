/**
 * 
 */
package com.reportolia.core.handler.operand;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;

import com.reportolia.core.model.operand.ContentType;
import com.reportolia.core.model.operand.Operand;
import com.reportolia.core.model.operand.OperandColumnPath;
import com.reportolia.core.model.operand.OperandOwnerType;
import com.reportolia.core.repository.operand.OperandColumnPathRepository;
import com.reportolia.core.repository.operand.OperandRepository;

/**
 * The QueryOperandManager class
 *
 * @author Batir Akhmerov
 * Created on Dec 11, 2015
 */
public class OperandManager implements OperandHandler {
	
	protected OperandOwnerType operandOwnerType;
	protected ContentType contentType;
	
	@Resource protected OperandRepository operandRepository;
	@Resource protected OperandColumnPathRepository operandColumnPathRepository;
	
	public static final Sort SORT_BY_ORDER = new Sort(Sort.Direction.ASC, "order");
	
	public OperandOwnerType getOperandOwnerType() {
		return this.operandOwnerType;
	}

	public void setOperandOwnerType(OperandOwnerType operandOwnerType) {
		this.operandOwnerType = operandOwnerType;
	}

	public ContentType getContentType() {
		return this.contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
	
	//////////////////////////////////////////////////////////
	
	public List<Operand> getOperandsByOwner(Long ownerId) {
		return this.operandRepository.findByOwnerIdAndOwnerTypeAndContentType(ownerId, this.operandOwnerType, this.contentType, SORT_BY_ORDER);
	}
	
	public List<OperandColumnPath> getOperandColumnPathsByOperand(Operand operand) {
		return this.operandColumnPathRepository.findByOperand(operand, SORT_BY_ORDER);
	}

	

}
