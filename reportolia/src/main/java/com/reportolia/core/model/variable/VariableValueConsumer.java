/**
 * 
 */
package com.reportolia.core.model.variable;

/**
 * The VariableValueConsumer class
 *
 * @author Batir Akhmerov
 * Created on Sep 29, 2016
 */
public class VariableValueConsumer {
	
	private VariableValueConsumerType type;
	private Long consumerId;
	
	public VariableValueConsumer() {
		this(null, null);
	}
	
	public VariableValueConsumer(VariableValueConsumerType type, Long id) {
		this.type = type;
		this.consumerId = id;
	}
	
	public static VariableValueConsumer newConsumerOperand(Long operandId){
		return new VariableValueConsumer(VariableValueConsumerType.OPERAND, operandId);
	}
	
	public static VariableValueConsumer newConsumerReportColumn(Long columnId){
		return new VariableValueConsumer(VariableValueConsumerType.REPORT_COLUMN, columnId);
	}
	
	
	public VariableValueConsumerType getType() {
		return this.type;
	}
	public void setType(VariableValueConsumerType type) {
		this.type = type;
	}
	public Long getConsumerId() {
		return this.consumerId;
	}
	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

}
