/**
 * 
 */
package com.reportolia.core.utils.block;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.reportolia.core.sql.query.model.QueryOperand;

/**
 * The Block class
 *
 * @author Batir Akhmerov
 * Created on Mar 11, 2016
 */
public class Block {
	
	private Block topBlock;
	private QueryOperand startOperand;
	private List<QueryOperand> list;
	private QueryOperand endOperand;
	private boolean stringIncluded;
	private List<Block> childBlocks = new ArrayList<>();
	
	Block(Block topBlock, QueryOperand startOperand) {
		this.topBlock = topBlock;
		if (this.topBlock != null) this.topBlock.addChildBlock(this);
		this.startOperand = startOperand;
	}
	
	public void refreshBlock() {
		if (this.startOperand != null) this.startOperand.setConvertToString(this.stringIncluded);
		for (QueryOperand operand: this.list) {
			operandToStringIfNeeded(operand);
		}
		for (Block childBlock: this.childBlocks) {
			operandToStringIfNeeded(childBlock.getStartOperand());
			operandToStringIfNeeded(childBlock.getEndOperand());
		}
		if (this.endOperand != null) {
			this.endOperand.setConvertToString(this.stringIncluded);
		}
		if (this.topBlock != null) {
			topBlock.setStringIncluded(this.stringIncluded);
			topBlock.refreshBlock();
		}
	}
	
	protected void operandToStringIfNeeded(QueryOperand operand) {
		Assert.isTrue(operand != null);
		if (!operand.isStringType() && !operand.isConvertToString()
				&& (operand.getDataType() != null || operand.isBlockStart() || operand.isBlockEnd())) {
			operand.setConvertToString(this.stringIncluded);
		}
	}

	public List<QueryOperand> getList() {
		if (this.list == null) {
			this.list = new ArrayList<>();
		}
		return this.list;
	}
	
	public void addOperand(QueryOperand operand) {
		getList().add(operand);
	}
	
	public void addChildBlock(Block block){
		this.childBlocks.add(block);
	}

	public QueryOperand getStartOperand() {
		return this.startOperand;
	}

	public void setStartOperand(QueryOperand startOperand) {
		this.startOperand = startOperand;
	}

	public QueryOperand getEndOperand() {
		return this.endOperand;
	}

	public void setEndOperand(QueryOperand endOperand) {
		this.endOperand = endOperand;
	}

	public Block getTopBlock() {
		return this.topBlock;
	}

	public void setTopBlock(Block topBlock) {
		this.topBlock = topBlock;
	}

	public boolean isStringIncluded() {
		return this.stringIncluded;
	}

	public void setStringIncluded(boolean stringIncluded) {
		this.stringIncluded = stringIncluded;
	}
}
